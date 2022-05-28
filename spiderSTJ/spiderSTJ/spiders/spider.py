from ast import parse
from requests import options
import scrapy
from selenium import webdriver 
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
import time
from selenium.webdriver import Chrome, ChromeOptions
from scrapy.utils.project import get_project_settings
#from spiderSTJ.items import AcordaoItem

class SpiderSpider(scrapy.Spider):
    name = 'spider'
    
    def __init__():
        
        settings = get_project_settings()
        driver_path = settings.get('CHROME_DRIVER_PATH')
        options = ChromeOptions()
        options.headless = False
        driver = Chrome(executable_path=driver_path, options=options)
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

            #html = driver.find_element_by_tag_name('html')
            #html = driver.page_source
        
        relatores = driver.find_elements(By.XPATH, '//div[contains(.,"Relator") and @class="docTitulo"]/following-sibling::div/pre')
            #print("\n relatores\n")
            #print(relatores)
            #print("\n tamanho\n")
            #print(len(relatores))
        print("\nrelatores texto\n")
        for relator in relatores:
            yield{relator.text}
        print("\n fim relatores\n")
        driver.close()

    def parse(self, response):
        #item = AcordaoItem()
        #item['relator'] = response.xpath('//div[contains(.,"Relator") and @class="docTitulo"]/following-sibling::div/pre/text()').get()
        #yield item
        #list = response.xpath('//div[contains(.,"Relator") and @class="docTitulo"]/following-sibling::div/pre/text()').get()
        #for i in list:
        #    print(i)
        pass

    def printLista(self,lista):
        for item in lista:
            yield{item.text}