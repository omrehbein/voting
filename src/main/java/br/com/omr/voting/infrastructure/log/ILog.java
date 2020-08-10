package br.com.omr.voting.infrastructure.log;

public interface ILog {
	public void info(String message, Object... params);
	public void trace(String message, Object... params);
	public void debug(String message, Object... params);
	public void error(String message, Object... params);
}
