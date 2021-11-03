package br.com.pedroxsqueiroz.stranding.services;

import java.io.InputStream;


public interface MediaService {

	InputStream get(String string);

	String set(InputStream inputStream);

}
