from selenium import webdriver 
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
import time
import re
from datetime import datetime

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

PATH = "C:/Users/wilki/TG/chromedriver.exe"
driver = webdriver.Chrome(PATH)
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

relatores = driver.find_elements(By.XPATH, '//div[contains(.,"Relator") and @class="docTitulo"]/following-sibling::div/pre')
datasJulgamentos = driver.find_elements(By.XPATH, '//div[contains(.,"Data do Julgamento") and @class="docTitulo"]/following-sibling::div/pre')
datasPublicacoes = driver.find_elements(By.XPATH, '//div[contains(.,"Data da Publicação/Fonte") and @class="docTitulo"]/following-sibling::div/pre')
idsProcessos = driver.find_elements(By.XPATH, '//div[contains(.,"Processo") and @class="docTitulo"]/following-sibling::div')
ementas = driver.find_elements(By.XPATH, '//div[contains(.,"Ementa") and @class="docTitulo"]/following-sibling::div/p')

for i in range(len(relatores)):
    print("\n")
    print('relator: '+ relatores[i].text)
    print('anoJulgamento: ' + str(splitYear(datasJulgamentos[i].text)))
    print('anoPublicacao: ' + str(splitYear(datasPublicacoes[i].text)))
    print('regiao: ' + splitRegiao(idsProcessos[i].text))
    print('numeroProcesso: ' + splitNumeroProcesso(idsProcessos[i].text))
    print('numeroProcessoRegiao: '+ splitNumeroProcessoRegiao(idsProcessos[i].text, splitNumeroProcesso(idsProcessos[i].text),splitRegiao(idsProcessos[i].text)))
    print('topicos: ')
    print(splitEmentasToTag(ementas[i].text))
    print("\n")

driver.close()


