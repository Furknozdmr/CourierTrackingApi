# CourierTrackingApi

# 📦 Proje Hakkında
CourierTrackingApi, kuryelerin konumlarını takip etmek, mağaza yakınlıklarını kontrol etmek ve toplam seyahat mesafelerini hesaplamak amacıyla geliştirilmiş bir RESTful API projesidir. Proje, özellikle lojistik ve dağıtım sektörlerinde kuryelerin performansını izlemek ve operasyonel verimliliği artırmak için tasarlanmıştır.

# ⚙️ Kullanılan Teknolojiler
-Java 17

-Spring Boot

-Spring Data JPA

-H2 In-Memory Database (Geliştirme ve test amaçlı)

-Lombok

-MapStruct

-Swagger / SpringDoc OpenAPI

-Maven

-GitHub

# 🧱 Mimari ve Tasarım Desenleri
Proje, Clean Architecture prensiplerine uygun olarak katmanlı bir yapı ile geliştirilmiştir:

Domain Katmanı: İş kuralları ve domain modellerini içerir.

Application Katmanı: Kullanım senaryoları (use cases) ve servisleri barındırır.

Infrastructure Katmanı: Veri erişimi ve dış servis entegrasyonlarını içerir.

Presentation Katmanı: REST API controller'ları ve DTO'ları içerir.

# Kullanılan başlıca tasarım desenleri:

Strategy Pattern: Farklı mesafe hesaplama stratejilerini (örneğin, Haversine) uygulamak için kullanılır.

Singleton Pattern: Bazı bileşenlerin tek bir örneğinin kullanılmasını sağlar.

Repository Pattern: Veri erişim katmanını soyutlamak için kullanılır.

Service Layer Pattern: İş mantığını servis katmanında toplar.

DTO Pattern: Veri transfer nesneleri ile veri taşımayı sağlar.

Command Pattern: Kullanım senaryolarını temsil eden komut nesneleri ile işlemleri modeller.

# Endpoint Curlleri

# GetAllStore
curl --location 'http://localhost:9090/courier-tracker-api/store/all' \
--header 'accept: */*'

# Total Distance 
curl --location 'http://localhost:9090/courier-tracker-api/courier/ahmet/totalDistance?distanceUnit=km' \
--header 'accept: */*'

# Save Log Location
curl --location 'http://localhost:9090/courier-tracker-api/courier/log-instant-location' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--data '{
"courierId": "ahmet",
"latitude": 40.9923307,
"longitude": 29.1244229,
"time": "2025-05-27T05:45:38.773Z"
}'

# Not 
Postman collection resources package içinde json olarak export edilmiştir.

# Swagger-ui

http://localhost:9090/courier-tracker-api/swagger-ui/index.html#/





