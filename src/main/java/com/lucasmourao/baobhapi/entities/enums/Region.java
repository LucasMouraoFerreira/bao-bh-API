package com.lucasmourao.baobhapi.entities.enums;

public enum Region {
	
	PAMPULHA(1),
	BARREIRO(2),
	CENTRO_SUL(3),
	LESTE(4),
	NORDESTE(5),
	NOROESTE(6),
	NORTE(7),
	OESTE(8),
	VENDA_NOVA(9);
	
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
