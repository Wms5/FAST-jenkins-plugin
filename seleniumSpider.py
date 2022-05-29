from selenium import webdriver 
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
import time
import re
from datetime import datetime
from pymongo import MongoClient

def splitEmentasToTag(str):
    b = str.split("\n")
    c = b[0].replace(" ","")[:-1].split(".")
    return c

def splitRegiao(regiao):
    b = regiao.split("\n")
    c = b[0].replace(" ","").split("/")[1]
    return c

def splitNumeroProcesso(str):
    b = str.split("\n")
    c = re.findall(r"(\d+)",b[0])
    return c[0]

def splitNumeroProcessoRegiao(str,numeroProcesso,regiao):
    b = str.split("\n")
    c = re.findall(r"{numeroProcesso} / {regiao}".format(numeroProcesso=numeroProcesso,regiao=regiao),b[0])
    return c[0]

def splitYear(str):
    b = re.findall(r"\d\d/\d\d/\d\d\d\d",str)
    c = datetime.strptime(b[0], '%d/%m/%Y')
    return c.year

def splitTipoProcesso(str):
    b = str.split("\n")
    return b[1]

def splitNome(str):
    c = re.findall(r"([a-zA-Z\sáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÒÖÚÇÑ]+)",str)
    return c[0]

PATH = "C:/Users/wilki/TG/chromedriver.exe"

client = MongoClient("mongodb+srv://wilkinson_maciel:1234567890@cluster0.izkuog8.mongodb.net/?retryWrites=true&w=majority")
db = client.acordao

driver = webdriver.Chrome(PATH)
driver.implicitly_wait(120)
driver.get("https://scon.stj.jus.br/SCON/pesquisar.jsp")
driver.maximize_window()
actions = ActionChains(driver)

html = driver.find_element_by_tag_name('html')
btn_pesquisaAvancada = driver.find_element(By.XPATH, '//button[@id="idMostrarPesquisaAvancada"]')
span_orgaoJulgador = driver.find_element(By.XPATH, '//span[@id="spanNomesOrgaos"]')
span_primeiraTurma = driver.find_element(By.XPATH, '//span[.="PRIMEIRA TURMA"]')
btn_buscar = driver.find_element(By.XPATH, '//button[@data-bs-original-title="Pesquisar"]')

btn_pesquisaAvancada.click()
span_orgaoJulgador.click()
span_primeiraTurma.click()
btn_buscar.click()



for j in range(10000):
    relatores = driver.find_elements(By.XPATH, '//div[contains(.,"Relator") and @class="docTitulo"]/following-sibling::div/pre')
    datasJulgamentos = driver.find_elements(By.XPATH, '//div[contains(.,"Data do Julgamento") and @class="docTitulo"]/following-sibling::div/pre')
    datasPublicacoes = driver.find_elements(By.XPATH, '//div[contains(.,"Data da Publicação/Fonte") and @class="docTitulo"]/following-sibling::div/pre')
    idsProcessos = driver.find_elements(By.XPATH, '//div[contains(.,"Processo") and @class="docTitulo"]/following-sibling::div')
    ementas = driver.find_elements(By.XPATH, '//div[contains(.,"Ementa") and @class="docTitulo"]/following-sibling::div/p')
    btn_proximaPagina = driver.find_element(By.XPATH, '(//a[@data-bs-original-title="Próxima página"])[1]')

    for i in range(len(relatores)):
        acordao = {
            'numeroProcesso' : splitNumeroProcesso(idsProcessos[i].text),
            'regiao' : splitRegiao(idsProcessos[i].text),
            'numeroProcessoRegiao': splitNumeroProcessoRegiao(idsProcessos[i].text, splitNumeroProcesso(idsProcessos[i].text),splitRegiao(idsProcessos[i].text)),
            'relator': splitNome(relatores[i].text),
            'anoJulgamento' : str(splitYear(datasJulgamentos[i].text)),
            'anoPublicacao' : str(splitYear(datasPublicacoes[i].text)),
            'tipoProcesso' : splitTipoProcesso(idsProcessos[i].text),
            'topicos' : splitEmentasToTag(ementas[i].text)

        }
        result=db.reviews.insert_one(acordao)
        time.sleep(1.0)
        print('Criado {0} de 10 na página {1}'.format(i,j))
    btn_proximaPagina.click()

print('finalizado criados 100 acordaos')
driver.close()


