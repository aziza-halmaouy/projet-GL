INSERT INTO stage_offers (title, company, location, description, duration, website)
SELECT 'Stage Java Spring Boot', 'TechCorp', 'Casablanca', 'Développement d''une application web complète avec Spring Boot.', '3 mois', 'https://techcorp.com'
    WHERE NOT EXISTS (SELECT 1 FROM stage_offers WHERE company = 'TechCorp');

INSERT INTO stage_offers (title, company, location, description, duration, website)
SELECT 'Stage Frontend React', 'WebSoft', 'Rabat', 'Conception et développement d''interfaces utilisateur modernes avec React.js.', '2 mois', 'https://websoft.com'
    WHERE NOT EXISTS (SELECT 1 FROM stage_offers WHERE company = 'WebSoft');

INSERT INTO stage_offers (title, company, location, description, duration, website)
SELECT 'Stage Full Stack Java/React', 'Digital Solutions', 'Casablanca', 'Participation au développement d''une application complète full stack.', '4 mois', 'https://digitalsolutions.com'
    WHERE NOT EXISTS (SELECT 1 FROM stage_offers WHERE company = 'Digital Solutions');

INSERT INTO stage_offers (title, company, location, description, duration, website)
SELECT 'Stage Data Analyst', 'DataVision', 'Tanger', 'Analyse de données d''entreprise, création de dashboards.', '3 mois', 'https://datavision.com'
    WHERE NOT EXISTS (SELECT 1 FROM stage_offers WHERE company = 'DataVision');

INSERT INTO stage_offers (title, company, location, description, duration, website)
SELECT 'Stage Mobile Flutter', 'AppCreators', 'Marrakech', 'Développement d''applications mobiles multiplateformes avec Flutter.', '3 mois', 'https://appcreators.com'
    WHERE NOT EXISTS (SELECT 1 FROM stage_offers WHERE company = 'AppCreators');

INSERT INTO stage_offers (title, company, location, description, duration, website)
SELECT 'Stage DevOps & Cloud', 'CloudNet', 'Casablanca', 'Apprentissage des outils DevOps : Docker, Kubernetes, CI/CD.', '4 mois', 'https://cloudnet.com'
    WHERE NOT EXISTS (SELECT 1 FROM stage_offers WHERE company = 'CloudNet');

INSERT INTO stage_offers (title, company, location, description, duration, website)
SELECT 'Stage Python / IA', 'AI Morocco', 'Rabat', 'Développement d''algorithmes d''intelligence artificielle avec Python.', '3 mois', 'https://aimorocco.com'
    WHERE NOT EXISTS (SELECT 1 FROM stage_offers WHERE company = 'AI Morocco');

INSERT INTO users (nom, prenom, email, password)
SELECT 'Halmaouy', 'Aziza', 'aziza@gmail.com', '1234'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'aziza@gmail.com');