from pymongo import MongoClient
# pprint library is used to make the output look more pretty
from pprint import pprint
# connect to MongoDB, change the << MONGODB URL >> to reflect your own connection string
client = MongoClient("mongodb+srv://wilkinson_maciel:1234567890@cluster0.izkuog8.mongodb.net/?retryWrites=true&w=majority")

db = client.acordao

acordao = {
        'id' : "0984545",
        'relator' : "Amara Maria",
        'dataJulgamento':"21/06/2020",
        'dataPublicacao': "25/07/2023",
        'tags': ['acordao','stj','teste', 'tcc'],
        'referencias': ['98123','32143']
}

result=db.reviews.insert_one(acordao)