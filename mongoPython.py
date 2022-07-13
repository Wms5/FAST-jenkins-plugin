from pymongo import MongoClient
# pprint library is used to make the output look more pretty
from pprint import pprint
import json
import time
# connect to MongoDB, change the << MONGODB URL >> to reflect your own connection string
client = MongoClient("mongodb+srv://wilkinson_maciel:1234567890@cluster0.izkuog8.mongodb.net/?retryWrites=true&w=majority")

db = client.acordao
db2 = client.topicos
# acordao = {
#         'id' : "0984545",
#         'relator' : "Amara Maria",
#         'dataJulgamento':"21/06/2020",
#         'dataPublicacao': "25/07/2023",
#         'tags': ['acordao','stj','teste', 'tcc'],
#         'referencias': ['98123','32143']
# }


#print(db.reviews.aggregate([{"$unwind": "$topicos"}]))



#for doc in db.reviews.aggregate([{"$unwind": "$topicos"},{ "$unset": "numeroProcesso"},{"$unset": "_id"},{"$unset": "regiao"},
#{"$unset": "numeroProcessoRegiao"},{"$unset": "relator"},{"$unset": "anoJulgamento"},{"$unset": "anoPublicacao"},{"$unset": "tipoProcesso"}]):
#        print(doc)
#        db2.reviews.insert_one(doc)
#        time.sleep(2.0)
#result=db.reviews.insert_one(acordao)

docs = db.reviews.find({'numeroProcesso':'RESP 848920-SP'},no_cursor_timeout=True)
for doc in docs:
        print(doc)


