package com.lucasmourao.baobhapi.entities.enums;

public enum Region {
	
	PAMPULHA(0),
	BARREIRO(1),
	CENTRO_SUL(2),
	LESTE(3),
	NORDESTE(4),
	NOROESTE(5),
	NORTE(6),
	OESTE(7),
	VENDA_NOVA(8);
	
	private int code;
	
	private Region(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static Region valueOf(int code) {
		for(Region value : Region.values()) {
			if(code == value.getCode()) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid Region code");
	}
}
