# ベースイメージを指定
FROM openjdk:21-jdk-slim

# 作業ディレクトリを設定
WORKDIR /app

# Jar ファイルをコンテナにコピー
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

# アプリケーションを実行
ENTRYPOINT ["java", "-jar", "app.jar"]