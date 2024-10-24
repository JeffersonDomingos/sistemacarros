package com.aps.sistemacarros.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aps.sistemacarros.config.SpringContext;
import com.aps.sistemacarros.model.Car;
import com.aps.sistemacarros.service.CarService;
import com.aps.sistemacarros.service.QRCodeService;
import com.itextpdf.html2pdf.HtmlConverter;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/carros")
public class CarController {
	@Autowired
	private CarService carService;

	@Autowired
	private QRCodeService qrCodeService;

	@PostMapping
	public ResponseEntity<Car> addCar(@RequestBody Car car) {
		return ResponseEntity.ok(carService.addCar(car));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
		return ResponseEntity.ok(carService.updateCar(id, car));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
		carService.deleteCar(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable Long id) {
		return ResponseEntity.ok(carService.getCarById(id));
	}

	@GetMapping
	public ResponseEntity<List<Car>> listCars() {
		return ResponseEntity.ok(carService.listCars());
	}

	@GetMapping("/relatorio")
	public void downloadCarReport(HttpServletResponse response, Model model) throws IOException {

		List<Car> carros = carService.listCars();

		model.addAttribute("carros", carros);

		String htmlContent = generateHtmlReport(model);

		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
		HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlContent.getBytes()), pdfOutputStream);

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=relatorio_carros.pdf");
		response.getOutputStream().write(pdfOutputStream.toByteArray());
	}

	private String generateHtmlReport(Model model) {
		org.thymeleaf.context.Context thymeleafContext = new org.thymeleaf.context.Context();
		thymeleafContext.setVariables(model.asMap());
		return SpringContext.getThymeleafTemplateEngine().process("carReport", thymeleafContext);
	}

	@GetMapping("/{id}/qrcode")
	public ResponseEntity<byte[]> generateCarQRCode(@PathVariable Long id) {
		Car car = carService.getCarById(id);
		String carInfo = String.format("Nome: %s, Marca: %s, Preco: %.2f, Ano: %s" , 
	            car.getModelo(), 
	            car.getMarca(), 
	            car.getPreco(),  
	            car.getAno());

	    byte[] qrCodeImage = qrCodeService.generateQRCode(carInfo, 300, 300);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeImage);
	}
}
