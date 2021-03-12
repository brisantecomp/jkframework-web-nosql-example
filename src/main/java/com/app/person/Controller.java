package com.app.person;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.jk.db.dataaccess.nosql.JKNoSqlDataAccess;
import com.jk.db.datasource.JKDataAccessFactory;
import com.jk.web.faces.mb.JKManagedBean;

@ManagedBean(name = "controller")
@ViewScoped
public class Controller extends JKManagedBean {
	JKNoSqlDataAccess da = JKDataAccessFactory.getNoSqlDataAccess();
	Model model;
	List<Model> modelList;
	List<Model> filterList;

	public boolean isAllowAdd() {
		return getModel().getId() == null;
	}

	public String add() {
		model.setId(UUID.randomUUID().toString());
		da.insert(model);
		refresh();
		success("Added Successfully");
		return null;
	}

	public boolean isAllowUpdate() {
		return getModel().getId() != null;
	}

	public String update() {
		da.update(model);
		String id = getModel().getId();
		success("Updated Successfully");
		refresh();
		// to ensure getting updated version from DB
		this.model = da.find(Model.class, id);
		return null;
	}

	public boolean isAllowDelete() {
		return getModel().getId() != null;
	}

	public String delete() {
		da.delete(getModel());
		success("Deleted Successfully");
		refresh();
		return null;
	}

	public String reset() {
		setModel(null);
		return null;
	}

	public Model getModel() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<Model> getModelList() {
		if (modelList == null) {
			modelList = da.getList(Model.class);
		}
		return modelList;
	}

	public List<Model> getFilterList() {
		return filterList;
	}

	public void setFilterList(Vector<Model> filterList) {
		this.filterList = filterList;
	}

	protected void refresh() {
		this.modelList = null;
		setModel(null);
	}
}
