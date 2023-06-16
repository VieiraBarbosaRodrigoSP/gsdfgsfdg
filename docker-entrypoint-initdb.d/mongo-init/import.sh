#! /bin/bash

mongoimport --jsonArray --host mongodbwish --db wishlist --collection wish --type json --file mongo-init/wish.json;