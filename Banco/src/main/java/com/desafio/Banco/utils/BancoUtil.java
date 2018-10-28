package com.desafio.Banco.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.vaadin.textfieldformatter.CustomStringBlockFormatter;
import org.vaadin.textfieldformatter.NumeralFieldFormatter;
import org.vaadin.textfieldformatter.CustomStringBlockFormatter.Options;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.TextField;

public class BancoUtil {

	public static OptionalMailValidator getOptionalMailValidator() {
		return new OptionalMailValidator();
	}

	public static ChangePasswordValidator getChangePasswordValidator() {
		return new ChangePasswordValidator();
	}

	public static PasswordValidator getPasswordValidator() {
		return new PasswordValidator();
	}
	
	public static void setContaField(TextField field) {
		Options options = new Options();
		options.setBlocks(9);
		options.setNumericOnly(true);
		new CustomStringBlockFormatter(options).extend(field);
	}
	
	public static void setValueField(TextField field) {
		new NumeralFieldFormatter("", ",", 2).extend(field);
	}
	
	static class OptionalMailValidator implements Validator<String> {
		private static final long serialVersionUID = 1L;

		@Override
		public ValidationResult apply(String value, ValueContext context) {
			return value.equals("") ? ValidationResult.ok()
					: new EmailValidator("Email inválido").apply(value, context);
		}
	}

	static class ChangePasswordValidator implements Validator<String> {
		private static final long serialVersionUID = 1L;

		@Override
		public ValidationResult apply(String value, ValueContext context) {
			return value.equals("") ? ValidationResult.ok() : new PasswordValidator().apply(value, context);
		}
	}

	static class PasswordValidator implements Validator<String> {
		private static final long serialVersionUID = 1L;

		@Override
		public ValidationResult apply(String value, ValueContext context) {
			if (value.length() < 4 || !value.matches(".*\\d.*"))
				return ValidationResult.error("Mínimo de 4 caracteres sendo pelo menos 1 numérico");
			return ValidationResult.ok();
		}
	}

	public static void setCPFFormato(TextField campo) {
		Options opcoes = new Options();
		opcoes.setBlocks(3, 3, 3, 2);
		opcoes.setNumericOnly(true);
		opcoes.setDelimiters(".", ".", "-");
		new CustomStringBlockFormatter(opcoes).extend(campo);
	}

	public static void setPhoneFormatter(TextField campo) {
		Options opcoes = new Options();
		opcoes.setBlocks(0, 2, 9);
		opcoes.setNumericOnly(true);
		opcoes.setDelimiters("(", ") ");
		new CustomStringBlockFormatter(opcoes).extend(campo);
	}

	public static boolean nomeValido(String name) {
		if (name.length() < 3) {
			return false;
		}
		return true;
	}
	
	public static Double stringVirgulaToDouble(String valor) {
		try {
			return NumberFormat.getInstance(Locale.FRANCE).parse(valor).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return -1d;
		}
	}
	
	public static Double fixCasasDecimais(Double num) {
		return Math.floor(num * 100 + 0.5) / 100;
	}
}
