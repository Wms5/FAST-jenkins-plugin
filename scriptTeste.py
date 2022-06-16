import re
from datetime import datetime
from PyPDF2 import PdfReader

reader = PdfReader("AGA 1278398.pdf")
number_of_pages = len(reader.pages)
for i in range(0, number_of_pages):
    page = reader.pages[i]
    text = page.extract_text()
    print(text)
    print(text.rfind("AgRg no REsp 394064-RJ"))