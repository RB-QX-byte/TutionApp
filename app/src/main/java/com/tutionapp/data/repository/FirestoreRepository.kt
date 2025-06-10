package com.tutionapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.tutionapp.data.model.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    
    // Generic functions for any collection
    private inline fun <reified T> getCollection(collectionName: String): Flow<List<T>> = callbackFlow {
        val listener = firestore.collection(collectionName)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val items = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject<T>()?.let { item ->
                        // Set ID field if it exists
                        when (item) {
                            is Student -> item.copy(id = doc.id) as T
                            is Assignment -> item.copy(id = doc.id) as T
                            is TutionClass -> item.copy(id = doc.id) as T
                            is Test -> item.copy(id = doc.id) as T
                            else -> item
                        }
                    }
                } ?: emptyList()
                
                trySend(items)
            }
        
        awaitClose { listener.remove() }
    }
    
    private suspend inline fun <reified T> addDocument(collectionName: String, item: T): String {
        val docRef = firestore.collection(collectionName).document()
        docRef.set(item as Any).await()
        return docRef.id
    }
    
    private suspend fun updateDocument(collectionName: String, id: String, updates: Map<String, Any>) {
        firestore.collection(collectionName).document(id).update(updates).await()
    }
    
    private suspend fun deleteDocument(collectionName: String, id: String) {
        firestore.collection(collectionName).document(id).delete().await()
    }
    
    // User operations
    suspend fun saveUser(user: User) {
        firestore.collection("users").document(user.uid).set(user).await()
    }
    
    suspend fun getUser(uid: String): User? {
        return try {
            firestore.collection("users").document(uid).get().await().toObject<User>()
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun updateUser(uid: String, updates: Map<String, Any>) {
        firestore.collection("users").document(uid).update(updates).await()
    }
    
    // Student operations
    fun getStudents(): Flow<List<Student>> = getCollection("students")
    
    suspend fun addStudent(student: Student): String = addDocument("students", student)
    
    suspend fun updateStudent(id: String, updates: Map<String, Any>) = updateDocument("students", id, updates)
    
    suspend fun deleteStudent(id: String) = deleteDocument("students", id)
    
    // Assignment operations
    fun getAssignments(): Flow<List<Assignment>> = getCollection("assignments")
    
    suspend fun addAssignment(assignment: Assignment): String = addDocument("assignments", assignment)
    
    suspend fun updateAssignment(id: String, updates: Map<String, Any>) = updateDocument("assignments", id, updates)
    
    suspend fun deleteAssignment(id: String) = deleteDocument("assignments", id)
    
    // Class operations
    fun getClasses(): Flow<List<TutionClass>> = getCollection("classes")
    
    suspend fun addClass(tutionClass: TutionClass): String = addDocument("classes", tutionClass)
    
    suspend fun updateClass(id: String, updates: Map<String, Any>) = updateDocument("classes", id, updates)
    
    suspend fun deleteClass(id: String) = deleteDocument("classes", id)
    
    // Test operations
    fun getTests(): Flow<List<Test>> = getCollection("tests")
    
    suspend fun addTest(test: Test): String = addDocument("tests", test)
    
    suspend fun updateTest(id: String, updates: Map<String, Any>) = updateDocument("tests", id, updates)
    
    suspend fun deleteTest(id: String) = deleteDocument("tests", id)
}
