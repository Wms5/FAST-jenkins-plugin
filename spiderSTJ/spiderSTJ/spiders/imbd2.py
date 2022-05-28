import scrapy


class Imbd2Spider(scrapy.Spider):
    name = 'imbd2'
    start_urls = ['https://www.imdb.com/chart/boxoffice/?ref_=nv_ch_cht']

    def parse(self, response):
        for filmes in response.xpath('//td[@class="titleColumn"]'):
            yield{
                'titulo': filmes.xpath('//td[@class="titleColumn"]/a/text()').get(),
                'total': filmes.xpath('//td[@class="ratingColumn"]/span/text()').get()
            }
        print("\n fim\n")

