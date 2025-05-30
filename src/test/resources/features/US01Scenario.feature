@US01FEATURE
Feature: US001 Kullanici Girisi Yaparak Urun Testi


  Scenario: Sayfaya giriş ve login
    Given Kullanici bir e-ticaret sitesini ziyaret eder
    And Kullanici giris islemi yapilir
    And Kullanici, "cep telefonu" aramasi yapar
    And Arama sonuclarinda fiyat araligi olarak 15000 – 20000 TL filtrelemesi yapilir
    And Filtrelenen sonuclarda en alt satirdan rastgele bir urun secilir ve urun detayina gidilir
    And Urun detayinda en düsük puanli saticinin urunu sepete eklenir
    And Urunun sepete dogru eklendigi kontrol edilir

