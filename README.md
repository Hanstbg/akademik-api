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

## Setup

### 1. Buat database PostgreSQL

```sql
CREATE DATABASE db_akademik_api;
```

### 2. Konfigurasi koneksi

```bash
cp src/main/resources/application.properties.template src/main/resources/application.properties
```

Edit `application.properties` sesuai PostgreSQL kamu.

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

Setelah login, tambahkan header berikut ke setiap request:
```
Authorization: Bearer <token_dari_login>
```

---

## Contoh Request (Postman)

### Register Dosen
```json
POST /api/auth/register
{
  "name": "Dr. Budi",
  "email": "budi@kampus.ac.id",
  "password": "password123",
  "role": "DOSEN"
}
```

### Register Mahasiswa
```json
POST /api/auth/register
{
  "name": "Hans Anderson",
  "email": "hans@mahasiswa.ac.id",
  "password": "password123",
  "role": "MAHASISWA"
}
```

### Buat Mata Kuliah (login sebagai DOSEN)
```json
POST /api/matakuliah
{
  "nama": "Pemrograman Berbasis Objek",
  "kode": "IF301",
  "deskripsi": "Belajar OOP dengan Java",
  "sks": 3
}
```

### Buat Tugas (login sebagai DOSEN)
```json
POST /api/tugas
{
  "judul": "Tugas 1 - REST API",
  "deskripsi": "Buat REST API sederhana menggunakan Spring Boot",
  "deadline": "2025-12-31T23:59:00",
  "mataKuliahId": "uuid-mata-kuliah"
}
```

### Kumpulkan Tugas (login sebagai MAHASISWA)
```json
POST /api/submission
{
  "tugasId": "uuid-tugas",
  "jawaban": "Link GitHub: https://github.com/hans/tugas1"
}
```

### Beri Nilai (login sebagai DOSEN)
```json
PUT /api/submission/{id}/nilai
{
  "nilai": 90,
  "catatan": "Implementasi sudah baik, endpoint lengkap"
}
```

---

## Test Coverage

```bash
# Jalankan semua test
mvn clean test

# Lihat laporan JaCoCo
mvn clean test && open target/site/jacoco/index.html
```

update 