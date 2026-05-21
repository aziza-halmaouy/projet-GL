# Utiliser une image Java
FROM eclipse-temurin:21-jdk

# Définir le répertoire de travail
WORKDIR /app

# Copier le jar généré
COPY target/gestion-stages-maroc-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port
EXPOSE 8080

# Commande de démarrage
ENTRYPOINT ["java", "-Dserver.port=$PORT", "-jar", "app.jar"]
