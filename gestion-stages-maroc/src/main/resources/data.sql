DELETE FROM applications;
DELETE FROM stage_offers;
DELETE FROM users;

INSERT INTO users (nom, prenom, email, password)
VALUES ('Halmaouy', 'Aziza', 'aziza@gmail.com', '1234');

INSERT INTO stage_offers (title, company, location, description, duration, website) VALUES
('Stage Java Spring Boot', 'TechCorp', 'Casablanca', 'Développement d''une application web complète avec Spring Boot.', '3 mois', 'https://techcorp.com'),
('Stage Frontend React', 'WebSoft', 'Rabat', 'Conception et développement d''interfaces utilisateur modernes avec React.js.', '2 mois', 'https://websoft.com'),
('Stage Full Stack Java/React', 'Digital Solutions', 'Casablanca', 'Participation au développement d''une application complète full stack.', '4 mois', 'https://digitalsolutions.com'),
('Stage Data Analyst', 'DataVision', 'Tanger', 'Analyse de données d''entreprise, création de dashboards.', '3 mois', 'https://datavision.com'),
('Stage Mobile Flutter', 'AppCreators', 'Marrakech', 'Développement d''applications mobiles multiplateformes avec Flutter.', '3 mois', 'https://appcreators.com'),
('Stage DevOps & Cloud', 'CloudNet', 'Casablanca', 'Apprentissage des outils DevOps : Docker, Kubernetes, CI/CD.', '4 mois', 'https://cloudnet.com'),
('Stage Python / IA', 'AI Morocco', 'Rabat', 'Développement d''algorithmes d''intelligence artificielle avec Python.', '3 mois', 'https://aimorocco.com');