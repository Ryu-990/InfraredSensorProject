# Infrared Sensor Project

## 概要
このプロジェクトは、**Arduino R4** と **赤外線センサー** を使用して、人や物の動きを検知し、サーバーに通知を送信するシステムです。  
サーバー側は **Jakarta EE** (Servlet & JSP) で構築され、データベース(MySQL)に記録を保存し、検知時にメール通知を送信します。  
また、外部の天気情報APIを利用し、検知時の気温や天候をデータベースに記録します。

## 主な機能
- **赤外線センサーの反応を検知**
- **Arduino からサーバーへ HTTP 通知**
- **サーバー側でデータベース(MySQL)に記録**
- **検知時にメール通知**
- **外部APIを利用し、検知時の気温・天気情報を取得し保存**

## 技術スタック
- **Arduino R4** + **R4HttpClient** ライブラリ
- **Jakarta EE** (Servlet / JSP)
- **MySQL**
- **JavaMail (Jakarta Mail)**
- **外部天気情報API (例: OpenWeatherMap)**

---

## 環境構築

### 1. Arduino のセットアップ
#### 必要なライブラリ
Arduino IDEで以下のライブラリをインストールしてください。
- `WiFi.h`
- `R4HttpClient.h`

#### `ISsketch_nov27a.ino` の書き込み
1. `ISsketch_nov27a.ino` を Arduino IDE で開く
2. `ssid` と `password` にWiFiの情報を設定
3. `serverURL` にサーバーのエンドポイントを設定
4. Arduino R4 へ書き込み

---

### 2. サーバーのセットアップ
#### 必要な環境
- **JDK 17**
- **Apache Tomcat 9.0.96**
- **MySQL**
- **Eclipse + Maven**

#### ビルドと実行
1. `git clone` でプロジェクトを取得
2. Eclipse で **Maven プロジェクト**としてインポート
3. `application.properties` にデータベース情報を設定
4. `mvn clean package` を実行し `.war` を生成
5. `Tomcat` にデプロイし、サーバーを起動

---

### 3. データベースの設定
#### MySQL にテーブルを作成
```sql
CREATE TABLE sensor_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    temperature FLOAT,
    weather VARCHAR(50),
    event_description VARCHAR(255)
);
