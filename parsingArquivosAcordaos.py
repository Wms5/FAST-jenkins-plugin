import keyword
from selenium import webdriver 
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
import time
import re
from datetime import datetime
from pymongo import MongoClient
from pyshadow.main import Shadow

PATH = "C:/Users/wilki/TG/chromedriver.exe"
options = webdriver.ChromeOptions()
options.add_experimental_option('prefs', {
"download.default_directory": "C:\\Users\\wilki\\TG\\arquivos",
"download.prompt_for_download": False, 
"download.directory_upgrade": True,
"plugins.always_open_pdf_externally": True

})
#options.add_argument('headless')
driver = webdriver.Chrome(PATH,options=options)
driver.implicitly_wait(120)
driver.get("https://scon.stj.jus.br/SCON/pesquisar.jsp")
driver.maximize_window()
actions = ActionChains(driver)

btn_pesquisaAvancada = driver.find_element(By.XPATH, '//button[@id="idMostrarPesquisaAvancada"]')
span_orgaoJulgador = driver.find_element(By.XPATH, '//span[@id="spanNomesOrgaos"]')
span_primeiraTurma = driver.find_element(By.XPATH, '//span[.="PRIMEIRA TURMA"]')
btn_buscar = driver.find_element(By.XPATH, '//button[@data-bs-original-title="Pesquisar"]')

btn_pesquisaAvancada.click()
span_orgaoJulgador.click()
span_primeiraTurma.click()
btn_buscar.click()

handles = driver.window_handles
parent_handle = driver.current_window_handle

btn_imprimir = driver.find_elements(By.XPATH, '//a[@data-bs-original-title="Exibir o documento em formato PDF para imprimir ou salvar."]')
for i in btn_imprimir:
    #body = driver.find_element(By.XPATH, '//body')
    actions.move_to_element(i).perform()
    i.click()
    time.sleep(10.0)
    handles = driver.window_handles
    driver.switch_to.window(handles[1])
    driver.close()
    #body.send_keys(Keys.ALT + Keys.F4)
    #body.send_keys(Keys.ENTER)
    driver.switch_to.window(parent_handle)
    time.sleep(10.0)
time.sleep(10.0)