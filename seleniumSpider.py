from selenium import webdriver 
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
import time

def yieldList(relatores):
    for relator in relatores:
        yield{relator.text}

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
idsProcessos = driver.find_elements(By.XPATH, '//div[contains(.,"Processo") and @class="docTitulo"]/following-sibling::div')
ementas = driver.find_elements(By.XPATH, '//div[contains(.,"Ementa") and @class="docTitulo"]/following-sibling::div/p')

for i in range(len(relatores)):
    print('relator: '+ relatores[i].text)
    print('data: ' + datasJulgamentos[i].text)
    print('idProcesso: ' + idsProcessos[i].text)
    print('ementa: ' + ementas[i].text)

#yieldList(relatores)

driver.close()


