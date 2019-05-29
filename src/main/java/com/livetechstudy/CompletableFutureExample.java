package com.livetechstudy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//https://gist.github.com/kumarpranav1987/1303e4baa4ca3df009ac59755eaa91ce
public class CompletableFutureExample {
	private static final Logger logger = LoggerFactory.getLogger(CompletableFutureExample.class);

	public static void main(String[] args) {
		logger.info("Main Started");
		Shop s = new Shop();
		
		//1.Below part is executed in different thread then main . Which thread pool?
		/*CompletableFuture.supplyAsync(() -> {
			return s.getPrice("XYZ");
		}).thenAccept((quote) -> {
			// thenAccept part is executed in same thread where supplyAsync is executed or in main thread if the supplyAsync part completes immediately
			//Comment delay() call in calculatePrice to verify 
			logger.info(quote.toString());
		});*/
		
		
		//2.Use your own executor for Completable future
		
		/*ExecutorService executor = Executors.newCachedThreadPool();
		CompletableFuture.supplyAsync(() -> {
			return s.getPrice("XYZ");
		}, executor).thenAccept((price) -> {
			logger.info(price.toString());
		});
		executor.shutdown();*/
		
		//3.Code to to show that thenAccept can run in different thread for of Common pool then the thread in which supplyAsync runs
		/*CompletableFuture<QuotedPricePrice> qp = CompletableFuture.supplyAsync(() -> {
			return s.getPrice("PQR");
		});
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		qp.thenAcceptAsync((price)->{logger.info(price.toString());});*/
		someOtherWork();
		
	}

	private static void someOtherWork() {
		logger.info("In Some other work");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Shop {
	private static final Logger logger = LoggerFactory.getLogger(Shop.class);

	public QuotedPricePrice getPrice(String product) {
		logger.info("Inside getPrice Method Calculating Price Of : " + product);
		return calculatePrice(product);
	}

	private QuotedPricePrice calculatePrice(String product) {
		delay();
		return new QuotedPricePrice(product, product.length() * 100);
	}

	private void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class QuotedPricePrice {
	private String product;
	private double printedPrice;

	public QuotedPricePrice(String product, double printedPrice) {
		this.product = product;
		this.printedPrice = printedPrice;
	}

	public double getPrintedPrice() {
		return printedPrice;
	}

	public void setPrintedPrice(double printedPrice) {
		this.printedPrice = printedPrice;
	}

	public String getPoduct() {
		return product;
	}

	public void setPoduct(String poduct) {
		this.product = poduct;
	}

	@Override
	public String toString() {
		return "QuotedPricePrice [poduct=" + product + ", printedPrice=" + printedPrice + "]";
	}
}
