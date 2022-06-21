import re
from datetime import datetime
from PyPDF2 import PdfReader

reader = PdfReader("AGA 1278398.pdf")
number_of_pages = len(reader.pages)
listIDAcordao = ["AgRg no REsp 394064-RJ","AG 1012811-RS","RESP 848920-SP"]
for i in range(0, number_of_pages):
    for j in range(len(listIDAcordao)):
        page = reader.pages[i]
        text = page.extract_text()
        #print(text)
        print(text.rfind(listIDAcordao[j]))