#check for root
uid=$(id -u)
if [ x$uid != x0 ]
then
    #Beware of how you compose the command
    printf -v cmd_str '%q ' "$0" "$@"
    exec sudo su -c "$cmd_str"
fi

#I am root
git pull https://github.com/aritnag/Product-Micro-Service-Docker-ELK.git
cd list-product-service
docker build -t remove-product-service .
docker tag add-product-service aritranag20/remove-product-service
docker push aritranag20/remove-product-service
docker-compose up
#and the rest of your commands