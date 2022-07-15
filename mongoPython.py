from pymongo import MongoClient
import pymongo
# pprint library is used to make the output look more pretty
from pprint import pprint
import json
import time
# connect to MongoDB, change the << MONGODB URL >> to reflect your own connection string
client = MongoClient("mongodb+srv://wilkinson_maciel:1234567890@cluster0.izkuog8.mongodb.net/?retryWrites=true&w=majority")

db = client.acordao
db2 = client.topicos
db3 = client.referencias
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
matchList = ['AgInt no AREsp 1978175 / SP','AgInt no AREsp 1333113 / RJ','AgInt no AREsp 1981305 / SP', 'AgInt no AREsp 1865094 / MG']
docs = db.reviews.find({'idProcesso': matchList[0]})

for doc in docs:
        print(doc)

idAcordao = matchList.pop(0)
print(idAcordao)

db.reviews.update_one({'idProcesso': idAcordao},{'$set':{'refererecias':matchList}})
for element in matchList:
        db3.reviews.insert_one({'referencia':element})

docs = db.reviews.find({'idProcesso': idAcordao})
for doc in docs:
        print(doc)


