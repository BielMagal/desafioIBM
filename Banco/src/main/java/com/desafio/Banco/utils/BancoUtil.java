package com.desafio.Banco.utils;

import org.vaadin.textfieldformatter.CustomStringBlockFormatter;
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
}
