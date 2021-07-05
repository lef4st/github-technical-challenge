package br.trustly.challenge.api.DTO;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import br.trustly.challenge.api.models.Extension;

/**
 * Class responsible for converting the <b>HashMap<String, Extension></b>  
 * into the <b>ExtensionsResponseDTO</b> DTO
 *
 */
public class ExtensionsResponseDTO {

	private List<Extension> data;

	public static ExtensionsResponseDTO create(HashMap<String, Extension> extensionsMap) {
		
		ExtensionsResponseDTO extensionsResponseDTO = new ExtensionsResponseDTO();
		
		extensionsResponseDTO.data = extensionsMap.values().stream().collect(Collectors.toList());
		
		return extensionsResponseDTO;
	}
	
	public ExtensionsResponseDTO() {}
	
	public ExtensionsResponseDTO(List<Extension> data) {
		super();
		this.data = data;
	}

	public List<Extension> getData() {
		return data;
	}

	public void setData(List<Extension> data) {
		this.data = data;
	}
}
