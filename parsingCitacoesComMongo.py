from os import listdir
from os.path import isfile, join
import re
import os
from datetime import datetime
from PyPDF2 import PdfReader
from pymongo import MongoClient
import pymongo
# pprint library is used to make the output look more pretty
from pprint import pprint
import json
import time

#listIDAcordao = ["AgRg no REsp 394064-RJ","AG 1012811-RS","RESP 848920-SP"]

client = MongoClient("mongodb+srv://wilkinson_maciel:1234567890@cluster0.izkuog8.mongodb.net/?retryWrites=true&w=majority")

db = client.acordao
db2 = client.topicos
db3 = client.referencias

with open("listaIDsAcordaos.txt", "r") as tf:
    listIDAcordao = tf.read().split('\n')

print(listIDAcordao)

PATH = "arquivos\\"
files = [f for f in listdir(PATH) if isfile(join(PATH, f))]
print(files)

for file in files:
    print("---------------------"+ file + "-----------------------")
    reader = PdfReader(str(PATH+file))
    number_of_pages = len(reader.pages)
    matchList = []
    for i in range(0, number_of_pages):
        for j in range(len(listIDAcordao)):
            page = reader.pages[i]
            text = page.extract_text()
            if text.rfind(listIDAcordao[j]) != -1:
                print(text.rfind(listIDAcordao[j]))
                print(listIDAcordao[j])
                matchList.append(listIDAcordao[j])
    idAcordao = matchList.pop(0)
    db.reviews.update_one({'idProcesso': idAcordao},{'$set':{'refererecias':matchList}})
    for element in matchList:
        db3.reviews.insert_one({'referencia':element})
    os.remove(str(PATH+file))