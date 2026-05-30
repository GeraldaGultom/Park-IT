[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/gqNSK9vH)
# INSTITUT TEKNOLOGI DEL
## UJIAN AKHIR SEMESTER
**Semester IV, T.A. 2025/2026**

| Informasi | Keterangan |
| :--- | :--- |
| **Hari, Tanggal** | Mei 2026 |
| **Kode/ Nama Mata Kuliah** | 12S2203/Pemrograman Berorientasi Objek |
| **Tipe Ujian** | Praktikum |
| **Waktu Pengerjaan** | 240 Menit |
| **Pengajar** | CHP, AMS |

---

## Petunjuk Umum
* Ujian dikerjakan secara individual.
* Ujian bersifat terbuka(local source). Anda tidak diperkenankan untuk mengakses internet selama pengerjaan.
* Perangkat ujian(Laptop) harus dalam mode pesawat, dan non aktifkan WiFi.
* Tidak diperkenankan untuk *sharing* pekerjaan dengan mahasiswa manapun. Pekerjaan dengan tingkat kemiripan >80% dipandang sebagai plagiasi.
* Setiap tindak kecurangan akan diproses sesuai dengan aturan yang berlaku. Integritas *source code* akan diperiksa dengan menggunakan JPlag.
* Submission dan video presentasi anda akan berkontribusi sebesar 60% pada nilai akhir UAS.

## Pengumpulan
1. Penilaian dan pengumpulan dilakukan secara otomatis menggunakan GitHub Classroom.
2. Setelah *submission deadline*, presentasikan pekerjaan anda dengan merekam layar (*full screen*) dan wajah anda.
* Video harus menunjukkan wajah dan layar penuh.  
* Jabarkan rationale implementasi, alir eksekusi, demonstrasi program, dan buktikan hasil di GitHub Classroom.
* Presentasikan pekerjaan anda dalam video minimal 10 menit (1080p).  
3. Upload video ke YouTube dengan *public*.
4. Kirimkan link video melalui Google Form yang ditentukan paling lama pada hari berikutnya pukul 08.00 WIB.



---

## F01 Park-IT
Pada sesi ini anda diminta untuk menyelesaikan sebuah persoalan untuk menguji pengetahuan serta keterampilan anda dalam menerapkan berbagai konsep yang telah dipelajari.

### Case Study
Di Institut Teknologi Del, penataan lokasi parkir kendaraan sivitas akademika menjadi prioritas. Setiap kendaraan harus diparkirkan pada area yang telah ditentukan sesuai dengan jenis kendaraannya. Terdapat beberapa area parkir yang dikhususkan untuk mobil (*car*) dan sepeda motor (*motorcycle*). Berikut adalah ketentuan yang diterapkan:
* Mobil (*car*) hanya dapat diparkirkan pada area parkir yang dikhususkan untuk mobil. Begitu pula dengan sepeda motor (*motorcycle*).
* Penempatan kendaraan ke dalam area parkir tidak boleh melebihi kapasitas yang tersedia.
* Kendaraan (*vehicle*) diidentifikasi dengan atribut: nomor polisi/plat (`plate_number`), nama pemilik (`owner`), dan jenis kendaraan (`type`). Keunikan kendaraan dijamin melalui `plate_number`.
* Area parkir (`parking_area`) diidentifikasi dengan: nama area (`name`), kapasitas maksimal (`capacity`), dan jenis kendaraan yang diizinkan (`allowed_type`). Keunikan area parkir dijamin melalui `name`.

### Codebase
Mensimulasikan studi kasus dalam solusi berbasis Java dengan mengaplikasikan konsep OO dan ORM berbasis JPA. Seluruh data disimpan pada database persisten. Anda dibebaskan menggunakan *driver database* (H2, MySQL, PostgreSQL, dsb.) sesuai pilihan masing-masing. 

Pada *repository* telah disediakan:
* `pom.xml`: Tambahkan dependensi driver database yang Anda pilih. Ubah `groupId` pada pom.xml menjadi Nomor Induk Mahasiswa (NIM) anda.
* `persistence.xml`: Konfigurasi ulang (JDBC URL, Driver Class, kredensial) sesuai database Anda.
* `pbo.f01.App`: Driver class utama.

---

## Tugas & Penilaian

### 1. Database Design (20 Poin)
Gambarkan struktur basisdata yang sesuai dalam diagram Physical Data Model (PDM). *(Dinilai secara manual).*

### 2. Entity Definition (20 Poin)
Kembangkan entity classes yang representatif. Definisikan mapping menggunakan annotation atau `persistence.xml`. *(Dinilai secara manual).*

### 3. Register Entities (20 Poin)
Fitur penambahan data kendaraan dan area parkir melalui STDIN. 
* Format Area: `area-add#<name>#<capacity>#<allowed_type>`
* Format Kendaraan: `vehicle-add#<plate_number>#<owner>#<type>`

<details>
<summary><b>Lihat Test Cases Tugas 3</b></summary>

* **Test Case 3.1: Tambah 1 Area (4 Poin)**
  * Input: `area-add#Area Parkir A#10#car` lalu `display-all`
  * Expected Output: `Area Parkir A car 10|0`
* **Test Case 3.2: Tambah Multi-Area Beda Tipe (4 Poin)**
  * Input: `area-add#Area Motor#15#motorcycle`, `area-add#Area Mobil#5#car`, lalu `display-all`
  * Expected Output: 
    ```text
    Area Mobil car 5|0
    Area Motor motorcycle 15|0
    ```
* **Test Case 3.3: Tambah Area Karakter Spesial (4 Poin)**
  * Input: `area-add#Area-GD722!#20#motorcycle` lalu `display-all`
  * Expected Output: `Area-GD722! motorcycle 20|0`
* **Test Case 3.4: Registrasi Kendaraan Tunggal (4 Poin)**
  * Input: `area-add#Area 1#1#car`, `vehicle-add#B123XX#Joko#car`, `park#B123XX#Area 1`, lalu `display-all`
  * Expected Output: 
    ```text
    Area 1 car 1|1
    B123XX Joko car
    ```
* **Test Case 3.5: Registrasi Multi-Kendaraan (4 Poin)**
  * Input: `area-add#Area VIP#5#car`, `vehicle-add#B1AA#Andi#car`, `vehicle-add#B2BB#Budi#car`, `park#B1AA#Area VIP`, `park#B2BB#Area VIP`, lalu `display-all`
  * Expected Output: 
    ```text
    Area VIP car 5|2
    B1AA Andi car
    B2BB Budi car
    ```
</details>

### 4. Assigning Vehicle (20 Poin)
Fitur menempatkan kendaraan ke area parkir. Validasi kapasitas dan jenis kendaraan.
* Format: `park#<plate number>#<area name>`

<details>
<summary><b>Lihat Test Cases Tugas 4</b></summary>

* **Test Case 4.1: Parkir Sesuai Ketentuan (5 Poin)**
  * Input: `area-add#Parkir M#10#motorcycle`, `vehicle-add#M123#Cita#motorcycle`, `park#M123#Parkir M`, lalu `display-all`
  * Expected Output: 
    ```text
    Parkir M motorcycle 10|1
    M123 Cita motorcycle
    ```
* **Test Case 4.2: Validasi Beda Tipe Kendaraan (5 Poin)**
  * Input: `area-add#Parkir M#10#motorcycle`, `vehicle-add#C123#Dani#car`, `park#C123#Parkir M`, lalu `display-all`
  * Expected Output: `Parkir M motorcycle 10|0`
* **Test Case 4.3: Validasi Melebihi Kapasitas (5 Poin)**
  * Input: `area-add#Parkir Ketat#1#car`, `vehicle-add#A1#Eka#car`, `vehicle-add#A2#Fani#car`, `park#A1#Parkir Ketat`, `park#A2#Parkir Ketat`, lalu `display-all`
  * Expected Output: 
    ```text
    Parkir Ketat car 1|1
    A1 Eka car
    ```
* **Test Case 4.4: Validasi Kendaraan Tidak Terdaftar (5 Poin)**
  * Input: `area-add#Area X#5#car`, `park#GHOST99#Area X`, lalu `display-all`
  * Expected Output: `Area X car 5|0`
</details>

### 5. Display All (20 Poin)
Menampilkan data area parkir (asc by name) dan kendaraan di dalamnya (asc by plate).

<details>
<summary><b>Lihat Test Cases Tugas 5</b></summary>

* **Test Case 5.1: Sorting Area Ascending (4 Poin)**
  * Input: `area-add#Zona Z#5#car`, `area-add#Zona C#5#car`, `area-add#Zona A#5#car`, lalu `display-all`
  * Expected Output: 
    ```text
    Zona A car 5|0
    Zona C car 5|0
    Zona Z car 5|0
    ```
* **Test Case 5.2: Sorting Kombinasi Area dan Kendaraan (4 Poin)**
  * Input: `area-add#B Area#5#car`, `area-add#A Area#5#motorcycle`, `vehicle-add#B2#Dua#car`, `vehicle-add#B1#Satu#car`, `vehicle-add#M2#Dua#motorcycle`, `vehicle-add#M1#Satu#motorcycle`, `park#B2#B Area`, `park#B1#B Area`, `park#M2#A Area`, `park#M1#A Area`, lalu `display-all`
  * Expected Output: 
    ```text
    A Area motorcycle 5|2
    M1 Satu motorcycle
    M2 Dua motorcycle
    B Area car 5|2
    B1 Satu car
    B2 Dua car
    ```
* **Test Case 5.3: Uji Publik Berdasarkan Contoh Soal (4 Poin)**
  * Input: Sesuai dengan "Contoh Interaksi I/O" pada soal
  * Expected Output: Sesuai dengan "Contoh Interaksi I/O" pada soal
</details>

---

## Contoh Interaksi I/O

**Input:**
```text
area-add#Area GD722#10#motorcycle
area-add#Area Rektorat#5#car
vehicle-add#BK1234AB#Chandro Pardede#car
vehicle-add#BB9988XY#Budi Santoso#motorcycle
vehicle-add#B123CD#Andi Wijaya#car
park#BK1234AB#Area Rektorat
park#B123CD#Area Rektorat
park#BB9988XY#Area GD722
display-all
 #   P a r k - I T  
 