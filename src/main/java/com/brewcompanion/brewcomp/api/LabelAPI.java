package com.brewcompanion.brewcomp.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.minio.Minio;
import com.brewcompanion.brewcomp.objects.Label;

@Controller
@RequestMapping(value = { "/api/labels" })
public class LabelAPI {

	@GetMapping("/getAvailableLabels")
	public ResponseEntity<Label[]> getAvailableLabels(
			@RequestParam(name = "userToken", required = false, defaultValue = "0") String userToken, Model model) {

		return ResponseEntity.ok(getLabelsFromMinio(userToken));
	}

	private Label[] getLabelsFromMinio(String userToken) {

		var labels = Minio.getLabels(userToken);

		if (labels.isPresent()) {
			return labels.get();
		} else {
			Main.getLogger().error("Failed to get labels from Minio");
			return new Label[0];
		}
	}

	@GetMapping("/getTemplate")
	public ResponseEntity<String> getTemplate(@RequestParam(name = "name", required = true) String name ) {
		return ResponseEntity.ok(Minio.getTemplate(name));	
	}
	

}
