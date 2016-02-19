package org.tahom.web.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;

public abstract class ModelAutoCompleteTextField<T> extends AutoCompleteTextField<T> {

	private static final long serialVersionUID = 1L;
	private List<T> allChoices;

	public ModelAutoCompleteTextField(String id, IModel<T> model, Class<T> type, IAutoCompleteRenderer<T> renderer,
	        AutoCompleteSettings settings, List<T> allChoices) {
		super(id, model, type, renderer, settings);
		this.allChoices = new ArrayList<T>(allChoices);
	}

	@Override
	protected Iterator<T> getChoices(String input) {
		List<T> choices = new ArrayList<T>();

		for (T object : allChoices) {
			if (add(input, object)) {
				choices.add(object);
			}
		}
		return choices.iterator();
	}

	@Override
	protected T convertValue(String[] value) throws ConversionException {
		if (getInput() != null) {
			for (T object : allChoices) {
				if (getInput().trim().equals(objectToString(object))) {
					allChoices.remove(object);
					return object;
				}
			}
		}
		return null;
	}

	protected abstract boolean add(String prefix, T object);

	protected abstract String objectToString(T object);

	public List<T> getAllChoices() {
		if (allChoices == null) {
			allChoices = new ArrayList<T>();
		}
		return allChoices;
	}

}
