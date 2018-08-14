#check for root
uid=$(id -u)
if [ x$uid != x0 ]
then
    #Beware of how you compose the command
    printf -v cmd_str '%q ' "$0" "$@"
    exec sudo su -c "$cmd_str"
fi

#I am root
echo "Inside  database-product-service"
rm -rf Product-Micro-Service-Docker-ELK
git clone https://github.com/aritnag/Product-Micro-Service-Docker-ELK.git

cd Product-Micro-Service-Docker-ELK/database-product-service
docker build -t database-product-service .
docker tag add-product-service aritranag20/database-product-service
docker push aritranag20/database-product-service
docker-compose up
#and the rest of your commands