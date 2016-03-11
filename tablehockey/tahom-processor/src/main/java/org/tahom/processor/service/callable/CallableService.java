package org.tahom.processor.service.callable;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.PdfTournamentException;
import org.tahom.processor.callable.AbstractPdfCallable;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CallableService {

	private Executor executor = Executors.newFixedThreadPool(3);
	private Cache<String, FutureTask<File>> cache;

	public CallableService() {
		cache = CacheBuilder.newBuilder().concurrencyLevel(4).maximumSize(10000).expireAfterWrite(1, TimeUnit.DAYS)
		        .build();
	}

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private FutureTask<File> executeCallable(Callable<File> callable) {
		FutureTask<File> futureTask = new FutureTask<File>(callable);
		executor.execute(futureTask);
		return futureTask;
	}

	public <I> void createFile(String uuid, String path, I input, Class<? extends AbstractPdfCallable<I>> callableClass) {
		try {
			logger.debug("Creating file with uuid" + uuid);
			AbstractPdfCallable<I> callable = callableClass.getConstructor(path.getClass(), input.getClass())
			        .newInstance(path, input);
			FutureTask<File> future = executeCallable(callable);
			cache.put(uuid, future);
		} catch (Exception ex) {
			logger.error("Error when creating file: ", ex);
			throw new PdfTournamentException(ex.getMessage());
		}
	}

	public File getFile(String uuid) {
		try {
			FutureTask<File> future = cache.getIfPresent(uuid);
			if (future != null) {
				return future.get();
			} else {
				throw new PdfTournamentException("File already expired.");
			}
		} catch (Exception ex) {
			logger.error("Error when getting file: ", ex);
			throw new PdfTournamentException(ex.getMessage());
		}
	}

	public void finalizeFile(String uuid) {
		cache.invalidate(uuid);
		logger.info("Cleaning file from cache with uuid: " + uuid);
	}
}
