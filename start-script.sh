
sudo docker-compose -f  news-chain/docker-compose.yml down

sudo docker stop $(sudo docker ps -a -q)


sudo rm -rf news-chain


sudo git clone https://github.com/mhmtonrn/news-chain.git


cd news-chain/


sudo mvn install

cd ..

sudo docker-compose -f news-chain/docker-compose.yml up -d
