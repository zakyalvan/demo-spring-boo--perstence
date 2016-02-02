#Menyimpan Data ke Database
##Intro
Ini adalah lanjutan dari tutorial yang kemarin, namun maju satu langkah, kita akan belajar bagaiamana cara menyimpan data ke database. Teknologi yang akan digunakan adalah JPA (Java Persistence API).

> Silahkan ikuti langkah demi langkah dalam tutorial ini, tapi tolong jangan copy paste, saya anggap kalian jujur, sampai kalian kedapatan ga jujur

##Persiapan Project
> Dalam tutorial ini, anggapannya kalian menggunakan Spring Tool Suite, jika menggunakan IDE lain, silahkan susuaikan.

###Buat Project Maven
1. Buat project maven seperti biasa, pilih menu __File__ > __New__ > __Maven Project__, 
2. Pada wizard centang 'Create a simple project (Skip archetype selection)' lalu Next.
3. Ketik Group Id (dalam contoh saya pake ```id.tomatech.tutorial```) dan Artifact Id (dalam contoh saya pake ```demo-spring-pkl--persistence```), field lainnya bisa diabaikan, lalu Finish. Setelah siap, isi file ```pom.xml``` seperti berikut ini.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>id.tomatech.tutorial</groupId>
	<artifactId>demo-spring-pkl--persistence</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</project>
```

4. Edit ```pom.xml```, set parent project, set juga properties sehingga kita gunakan java 8, sehingga isi ```pom.xml``` berubah seperti berikut ini

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>id.tomatech.tutorial</groupId>
	<artifactId>demo-spring-pkl--persistence</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.3.2.RELEASE</version>
	</parent>
	
	<properties>
		<java.version>1.8</java.version>
	</properties>
</project>
```

5. Tambahkan library spring boot untuk membuat aplikasi web dan menyimpan ke database. Selanjutnya file ```pom.xml``` akan berubah seperti berikut

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>id.tomatech.tutorial</groupId>
	<artifactId>demo-spring-pkl--persistence</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.3.2.RELEASE</version>
	</parent>
	
	<properties>
		<java.version>1.8</java.version>
	</properties>
	
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
	</dependencies>
</project>
```

7. Update project maven, klik kanan pada project dalam __Package Explorer__, pilih menu __Maven__ > __Update Project__.

6. Tambahkan library driver database untuk java (JDBC), sekarang bisa di pilih, mysql atau postgresql, tergantung dari preferensi atau database yg terinstall dikomputer kalian.

Tambahkan ke dependencies dalam ```pom.xml``` salah satu dari dependency berikut ini

```xml
<!-- Menggunakan postgresql database -->
<dependency>
	<groupId>org.postgresql</groupId>
	<artifactId>postgresql</artifactId>
</dependency>
```

```xml
<!-- Menggunakan mysql database -->
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
```

Dalam tutorial ini saya menggunakan postgresql, sehingga ```pom.xml``` menjadi

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>id.tomatech.tutorial</groupId>
	<artifactId>demo-spring-pkl--persistence</artifactId>
	<version>0.0.1-SNAPSHOT</version>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.3.2.RELEASE</version>
	</parent>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<!-- Menggunakan postgresql database -->
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
		</dependency>
	</dependencies>
</project>
```

###Membuat Database
Untuk menyimpan data ke database, kita harus membuat juga databasenya. Buat database sesuai dengan pilihan sebelumnya (mysql atau postgresql), pilih nama database sesuai preferensi (dalam contoh ini saya gunakan nama ```tutorial-spring-jpa```).

Kemudian, dalam project maven yg sebelumnya sudah dibuat, bikin file baru ```application.properties``` dalam directory ```src/main/resources```. Edit isinya dengan konfigurasi konenksi database kita

```
## Jika menggunakan postgresql database.
spring.datasource.url=jdbc:postgresql://localhost/tutorial-spring-dan-jpa

## Jika menggunakan mysql database
#spring.datasource.url=jdbc:mysql://localhost/tutorial-spring-dan-jpa

spring.datasource.username=username-db
spring.datasource.password=passward-db
```

##Mulai Tulis Kode
###Main Class
Buat main class, tujuannya adalah untuk menjalankan aplikasi tutorial kita, seperti berikut ini. Isinya tidak ada yg berbeda dari yang kita buat pada tutorial/demo sebelumnya. Dari directory ```src/main/java``` project, klik kanan lalu __New__ > __Class__. Ketik Package ```id.tomatech.tutorial``` dan Name ```TutorialApplication```. Edit isinya hingga seperti berikut ini isinya

```java
package id.tomatech.tutorial;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TutorialApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(TutorialApplication.class)
			.run(args);
	}
}
```

Untuk memastikan project yang kita setup sebelumnya benar (termasuk koneksi database), jalankan main kelas ini, klik kanan pada kelas, __Run As__ > __Java Application__ (atau __Run As__ > __Spring Boot App__ sama saja). Perhatikan Console, jika tidak ada pesan error artinya yg disiapkan sebelumnya sudah benar. Jika ada error, cek lagi apa yg salah, perbaiki sebelum lanjut.

###Entity Class
Singkatnya, entity class ini adalah kelas yg kita gunakan untuk memodelkan sebuah konsep sekaligus mapping ke table database.

> Kita masih menggunakan konsep pada tutorial atau demo sebelumnya, menyimpan data pribadi orang ```Person```, perbedaannya kali ini kita akan menyimpan dalam database.

Buat kelas person, pada package yg sama seperti sebelumnya (```id.tomatech.tutorial```), ubah sehinnga menjadi seperti pada potongan kode berikut ini

```java
package id.tomatech.tutorial;

/**
 * Import tidak perlu diketik, di STS gunakan Ctrl+Shift+O
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Comment ini tidak perlu ditulis lagi, baca aja :)
 *
 * Entity class ini di mapping ke table database dengan name 'people'.
 * Masing masing property dari kelas di mapping ke column table tersebut,
 * nama column sesuai dengan attribut name pada anotasi &#64;Column.
 * 
 * @author zakyalvan
 */
@Entity
@Table(name="people")
public class Person implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="gender")
	private String gender;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
```

Kemudian tambahkan baris konfigurasi berikut ini ke dalam ```application.properties```. Baris konfigurasi ini untuk mamaksa agar table database ```people``` yang dimapping ke entity ```Person``` automatis digenerate.

```
spring.jpa.generate-ddl=true
```

Sekarang coba jalankan lagi main class ```TutorialApplication```, sekarang pastikan satu table baru dibuat di database. Jika belum ada, berarti ada yg salah, koreksi dulu sebelum lanjut.

###Repository
Selanjutnya kita akan membuat repository. Repository adalah antarmuka untuk berinteraksi dengan database, misalnya pmenyimpan data ```Person``` ke database, mencari berdasarkan id, mengambil (query) list dari person yg sudah disimpan sebelumnya, dan lain lain, silahkan explore sendiri nanti.

Buat interface (bukan kelas) ```PersonRepository```. Kemudian ubah isinya sehingga isinya seperti berikut ini

```java
package id.tomatech.tutorial;

/**
 * Import tidak perlu diketik, di STS gunakan Ctrl+Shift+O
 */
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Comment ini juga tidak perlu ditulis lagi, baca aja :)
 *
 * Ini adalah antarmuka ke table database untuk entity {@link Person}.
 * Detailnya akan dijelaskan tutorial selanjutnya.
 * 
 * @author zakyalvan
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
	
}
```

Ya, cuma interface, object dari PersonRepository ini akan dibuat oleh internal Spring, selanjutnya kita tinggal menggunakan.

##Controller Class
Seperti yg sudah dibahas sebelumnya, controller adalah kelas untuk meng-handle request dari client (browser atau mobile app). Mirip dengan yg sebelumnya kita coba, _list people_, _find person by id_, _add_.

Buat kelas ```PeopleController```, lalu edit isinya sehingga seperti potongan kode berikut ini.

```java
package id.tomatech.tutorial;

/**
 * Import tidak perlu diketik, di STS gunakan Ctrl+Shift+O
 */
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment ini juga tidak perlu ditulis lagi, baca aja :)
 *
 * Handle request dari client, browser atau mobile app.
 * 
 * @author zakyalvan
 */
@RestController
@RequestMapping(value="/people")
public class PeopleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonRepository personRepository;
}
```

###Handle Add/Save Person Request
Untuk tujuan ini, buat satu method dalam ```PeopleController```, seperti potongan kode berikut ini

```java
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public HttpEntity<Person> registerPerson(@RequestBody Person person) {
		Person newPerson = personRepository.save(person);
		return new ResponseEntity<>(newPerson, HttpStatus.OK);
	}
```

Coba jalankan aplikasinya, kemudian test menggunakan Postman (atau curl). Pada Postman, gunakan __URL__ ```http://localhost:8080/people/register```, method __POST__, pata tab __Headers__ tambahkan Header dengan name __Content-Type__ dan value ```application/json```, kemudian pada tab __Body__ isi datanya dengan json berikut ini.
```json
{
	"name": "Zaky Alvan",
	"email": "zaky@example.com",
	"gender": "MALE"
}
```

> Field id tidak perlu diberikan, karena akan digenerate di server.

Cek dalam table database ```people```, apakah ada record baru yg masuk. Atau check melalu browser dengan alamat ```http://localhost:8080/people/list```.

###Handle List People Request
Untuk tujuan ini, buat satu method dalam ```PeopleController```, seperti potongan kode berikut ini
```java
	@RequestMapping(value= "/list", method=RequestMethod.GET)
	public Collection<Person> listPeople() {
		LOGGER.info("List people");
		return personRepository.findAll();
	}
```

Coba di web browser, arahkan ke url ```http://localhost:8080/people/list```.

###Handle Find Person By Id
Untuk tujuan ini, buat satu method dalam ```PeopleController```, seperti potongan kode berikut ini
```java
	@RequestMapping(value="/detail/{personId}", method=RequestMethod.GET)
	public HttpEntity<Person> detailPerson(@PathVariable(value="personId") Long id) {
		LOGGER.info("Retrieve person with id {}", id);
		if(!personRepository.exists(id)) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Person>(personRepository.findOne(id), HttpStatus.OK);
	}
```

Coba di web browser, arahkan ke url ```http://localhost:8080/people/detail/1```. Dengan anggapan, Person dengan id 1 ada dalam database.

##Tugas
- Commit dan push project yang kalian coba ke github kalian, email ke saya alamatnya.
- Untuk yang handle client android, bikin apliasi sederhana android untuk consume data dari aplikasi yg dibuat dari tutorial ini. Push ke github, dan email juga ke saya alamatnya.

