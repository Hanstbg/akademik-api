# Akademik API - Sistem Manajemen Tugas Kuliah

REST API backend untuk manajemen tugas kuliah dengan **Role-Based Access Control (RBAC)**. Dosen dan Mahasiswa memiliki hak akses yang berbeda di setiap endpoint.

## Fitur

| Fitur | Dosen | Mahasiswa |
|-------|-------|-----------|
| Kelola Mata Kuliah | ✅ CRUD | 👁 Lihat saja |
| Kelola Tugas | ✅ CRUD | 👁 Lihat saja |
| Beri Nilai | ✅ | ❌ |
| Enrollment | ❌ | ✅ Daftar/keluar |
| Kumpulkan Tugas | ❌ | ✅ |
| Lihat Nilai Sendiri | ❌ | ✅ |

## Tech Stack

- Java 21
- Spring Boot 3.4.0
- Spring Security + JWT (jjwt 0.12.6) + **RBAC**
- Spring Data JPA + PostgreSQL
- Maven

---

## Setup

### 1. Buat database PostgreSQL

```sql
CREATE DATABASE db_akademik_api;
```

### 2. Konfigurasi koneksi

Salin file template:

```bash
cp src/main/resources/application.properties.template src/main/resources/application.properties
```

Edit `application.properties` dan sesuaikan:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_akademik_api
spring.datasource.username=postgres
spring.datasource.password=<password_kamu>
```

### 3. Jalankan aplikasi

```bash
mvn spring-boot:run
```

Aplikasi berjalan di: `http://localhost:8080`

---

## API Endpoints

### Auth (Terbuka)
| Method | URL | Deskripsi |
|--------|-----|-----------|
| POST | `/api/auth/register` | Daftar akun (role: DOSEN / MAHASISWA) |
| POST | `/api/auth/login` | Login, mendapat JWT token |

### Mata Kuliah
| Method | URL | Role | Deskripsi |
|--------|-----|------|-----------|
| GET | `/api/matakuliah` | Semua | Lihat semua mata kuliah |
| GET | `/api/matakuliah/saya` | DOSEN | Mata kuliah milik dosen |
| GET | `/api/matakuliah/{id}` | Semua | Detail mata kuliah |
| POST | `/api/matakuliah` | DOSEN | Buat mata kuliah baru |
| PUT | `/api/matakuliah/{id}` | DOSEN | Update mata kuliah |
| DELETE | `/api/matakuliah/{id}` | DOSEN | Hapus mata kuliah |

### Enrollment
| Method | URL | Role | Deskripsi |
|--------|-----|------|-----------|
| GET | `/api/enrollment` | MAHASISWA | Lihat mata kuliah yang diikuti |
| POST | `/api/enrollment/{mataKuliahId}` | MAHASISWA | Daftar mata kuliah |
| DELETE | `/api/enrollment/{mataKuliahId}` | MAHASISWA | Keluar dari mata kuliah |

### Tugas
| Method | URL | Role | Deskripsi |
|--------|-----|------|-----------|
| GET | `/api/tugas/matakuliah/{mataKuliahId}` | Semua | Lihat tugas per mata kuliah |
| GET | `/api/tugas/{id}` | Semua | Detail tugas |
| POST | `/api/tugas` | DOSEN | Buat tugas baru |
| PUT | `/api/tugas/{id}` | DOSEN | Update tugas |
| DELETE | `/api/tugas/{id}` | DOSEN | Hapus tugas |

### Submission
| Method | URL | Role | Deskripsi |
|--------|-----|------|-----------|
| GET | `/api/submission/saya` | MAHASISWA | Lihat submission sendiri |
| GET | `/api/submission/tugas/{tugasId}` | DOSEN | Lihat semua submission per tugas |
| POST | `/api/submission` | MAHASISWA | Kumpulkan tugas |
| PUT | `/api/submission/{id}/nilai` | DOSEN | Beri nilai submission |

---

## Cara Pakai Token

Setelah login, salin token dari response lalu tambahkan ke setiap request sebagai header:

```
Authorization: Bearer <token_dari_login>
```

---

## Contoh Request

### Register & Login

```powershell
# Register Mahasiswa
Invoke-RestMethod -Method POST -Uri "http://localhost:8080/api/auth/register" `
  -ContentType "application/json" `
  -Body '{"name":"Hans Anderson","email":"hans@example.com","password":"password123","role":"MAHASISWA"}'

# Register Dosen
Invoke-RestMethod -Method POST -Uri "http://localhost:8080/api/auth/register" `
  -ContentType "application/json" `
  -Body '{"name":"Dr. Budi","email":"budi@example.com","password":"password123","role":"DOSEN"}'

# Login dan simpan token
$response = Invoke-RestMethod -Method POST -Uri "http://localhost:8080/api/auth/login" `
  -ContentType "application/json" `
  -Body '{"email":"budi@example.com","password":"password123"}'
$token = $response.data.token
```

### Buat Mata Kuliah (DOSEN)

```powershell
Invoke-RestMethod -Method POST -Uri "http://localhost:8080/api/matakuliah" `
  -ContentType "application/json" `
  -Headers @{Authorization="Bearer $token"} `
  -Body '{"nama":"Pemrograman Berbasis Objek","kode":"IF301","deskripsi":"Belajar OOP dengan Java","sks":3}'
```

### Buat Tugas (DOSEN)

```powershell
Invoke-RestMethod -Method POST -Uri "http://localhost:8080/api/tugas" `
  -ContentType "application/json" `
  -Headers @{Authorization="Bearer $token"} `
  -Body '{"judul":"Tugas 1 - REST API","deskripsi":"Buat REST API sederhana","deadline":"2025-12-31T23:59:00","mataKuliahId":"<uuid-mata-kuliah>"}'
```

### Kumpulkan Tugas (MAHASISWA)

```powershell
Invoke-RestMethod -Method POST -Uri "http://localhost:8080/api/submission" `
  -ContentType "application/json" `
  -Headers @{Authorization="Bearer $mahasiswaToken"} `
  -Body '{"tugasId":"<uuid-tugas>","jawaban":"Link GitHub: https://github.com/hans/tugas1"}'
```

### Beri Nilai (DOSEN)

```powershell
Invoke-RestMethod -Method PUT -Uri "http://localhost:8080/api/submission/<uuid-submission>/nilai" `
  -ContentType "application/json" `
  -Headers @{Authorization="Bearer $token"} `
  -Body '{"nilai":90,"catatan":"Implementasi sudah baik, endpoint lengkap"}'
```

---

## Test Coverage

```bash
# Jalankan semua test
mvn clean test

# Lihat laporan JaCoCo
mvn clean test
# Buka file: target/site/jacoco/index.html
```
