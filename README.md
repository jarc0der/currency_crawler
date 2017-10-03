# currency_crawler

Task: 
Parse data from https://coinmarketcap.com/currencies/bitcoin/historical-data/ site and save each table CSV file.

Goal is to parse more than 100 links in short terms.

We use parallel stream for parsing links, so our results for now is: ~15 sec. on 4 CPU processor :) 
