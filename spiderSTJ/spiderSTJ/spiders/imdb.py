import scrapy


class ImdbSpider(scrapy.Spider):
    name = 'imdb'
    start_urls = ['https://www.imdb.com/chart/boxoffice/?ref_=nv_ch_cht']

    def parse(self, response):
        
        print("\n Response \n")
        print(response)
        print("\n fim response \n")

        print("\n Lista: \n")
        list = response.xpath('//td[@class="titleColumn"]/a/text()').getall()
        for i in list:
            yield{'titulo':i}
        print("\n fim\n")
