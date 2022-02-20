cd site
call npm run build --production
cd ..
call .\mvnw.cmd clean package -f pom.xml
docker-compose -f .\docker-compose-loader.yml down
docker-compose -f .\docker-compose-app.yml down
docker-compose -f .\docker-compose-loader.yml up --build -d
docker-compose -f .\docker-compose-app.yml up --build -d

pause;