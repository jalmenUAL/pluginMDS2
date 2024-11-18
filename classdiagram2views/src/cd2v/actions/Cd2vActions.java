package cd2v.actions;

import java.awt.Component;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.ViewManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.model.IActor;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IRelationship;
import com.vp.plugin.model.IStep;
import com.vp.plugin.model.IStepContainer;
import com.vp.plugin.model.IUseCase;
import com.vp.plugin.model.factory.IModelElementFactory;
import com.vp.plugin.view.IDialogHandler;

//LOWERCASE FOR NAMES OF .TS AND .JAVA!!

public class Cd2vActions implements VPActionController {
	public void performAction(VPAction action) {
		// get the view manager and the parent component for modal the dialog.
		ViewManager viewManager = ApplicationManager.instance().getViewManager();
		Component parentFrame = viewManager.getRootFrame();
		
		// popup a file chooser for choosing the output file
		JFileChooser fileChooser = viewManager.createJFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 
		fileChooser.showSaveDialog(parentFrame);
		File file = fileChooser.getSelectedFile();
		
		
		 
			 
			String result = "";
			String result2 = "";
			
			 
			ProjectManager projectManager = ApplicationManager.instance().getProjectManager();
			
			
			
			
			IModelElement[] models = projectManager.getProject().toModelElementArray(IModelElementFactory.MODEL_TYPE_USE_CASE);

			 
			for (int i = 0; i < models.length; i++) {
				IModelElement modelElement = models[i];
				IUseCase useCase = (IUseCase)modelElement;
				if (inheritanceUseCases(useCase)) {
				if (useCase.hasStereotype("business") || useCase.hasStereotype("event"))
				{}
				else {
				
				String Content =  generateTSUseCase(useCase);
				String Content2 =  generateJavaUseCase(useCase);
				FileWriter writer;
				FileWriter writer2;
				try {
					
					writer = new FileWriter(new File(file, "vista-"+clean(useCase.getName()).toLowerCase()+".ts"));
					writer.write(Content);
					writer.close();
					writer2 = new FileWriter(new File(file, "Vista"+upper(clean(useCase.getName()).toLowerCase())+".java"));
					writer2.write(Content2);
					writer2.close();
					result = "Success! Code generated for vista-"+clean(useCase.getName()).toLowerCase()+".ts";
					result2 = "Success! Code generated for Vista"+upper(clean(useCase.getName()).toLowerCase())+".java";
					if (useCase.hasStereotype("list")) {
						String Content3 =  generateTSUseCaseItem(useCase);
						String Content4 =  generateJavaUseCaseItem(useCase);
						writer = new FileWriter(new File(file, "vista-"+clean(useCase.getName()).toLowerCase()+"_item.ts"));
						writer.write(Content3);
						writer.close();
						writer2 = new FileWriter(new File(file, "Vista"+upper(clean(useCase.getName()).toLowerCase())+"_item.java"));
						writer2.write(Content4);
						writer2.close();
						
					}
					
					viewManager.showMessage(result);
					viewManager.showMessage(result2);
					 
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				}
				}
				 
				
			}
				

				IModelElement[] models2 = projectManager.getProject().toModelElementArray(IModelElementFactory.MODEL_TYPE_ACTOR);

				 
				for (int i = 0; i < models2.length; i++) {
					IModelElement modelElement = models2[i];
					IActor actor = (IActor)modelElement;
					if (inheritanceActors(actor)) { 
					if (actor.hasStereotype("external"))
					{}
					else
					{
					String Content =  generateTSActor(actor);
					String Content2 =  generateJavaActor(actor);
					FileWriter writer;
					FileWriter writer2;
					try {
						writer = new FileWriter(new File(file, "vista-"+clean(actor.getName()).toLowerCase()+".ts"));
						writer.write(Content);
						writer.close();
						writer2 = new FileWriter(new File(file, "Vista"+upper(clean(actor.getName()).toLowerCase())+".java"));
						writer2.write(Content2);
						writer2.close();
						result = "Success! Code generated for vista-"+clean(actor.getName()).toLowerCase()+".ts";
						result2 = "Success! Code generated for Vista"+upper(clean(actor.getName()).toLowerCase())+".java";
						viewManager.showMessage(result);
						viewManager.showMessage(result2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					}
					}
			 
				}	
			 
			
		 
	}
	
	public String upper(String s) {
		
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public String clean(String s) {
	
		s = s.replaceAll("[^a-zA-Z0-9]", "");  
		return s;
	}
	
	public String generateTSUseCase(IUseCase usecase) {
		
		String content = "import { LitElement, html, css, customElement } from 'lit-element';\r\n"
				+ "\r\n"
				+ "@customElement('vista-"+clean(usecase.getName()).toLowerCase()+"')\r\n"
				+ "export class "+"Vista"+upper(clean(usecase.getName()).toLowerCase())+" extends LitElement {\r\n"
				+ "  static get styles() {\r\n"
				+ "    return css`\r\n"
				+ "      :host {\r\n"
				+ "          display: block;\r\n"
				+ "          height: 100%;\r\n"
				+ "      }\r\n"
				+ "      `;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  render() {\r\n"
				+ "    return html``;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  // Remove this method to render the contents of this view inside Shadow DOM\r\n"
				+ "  createRenderRoot() {\r\n"
				+ "    return this;\r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "";
		
		return content;
		
	}
	
public String generateTSUseCaseItem(IUseCase usecase) {
		
		String content = "import { LitElement, html, css, customElement } from 'lit-element';\r\n"
				+ "\r\n"
				+ "@customElement('vista-"+clean(usecase.getName()).toLowerCase()+"_item')\r\n"
				+ "export class "+"Vista"+upper(clean(usecase.getName()).toLowerCase())+"_item extends LitElement {\r\n"
				+ "  static get styles() {\r\n"
				+ "    return css`\r\n"
				+ "      :host {\r\n"
				+ "          display: block;\r\n"
				+ "          height: 100%;\r\n"
				+ "      }\r\n"
				+ "      `;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  render() {\r\n"
				+ "    return html``;\r\n"
				+ "  }\r\n"
				+ "\r\n"
				+ "  // Remove this method to render the contents of this view inside Shadow DOM\r\n"
				+ "  createRenderRoot() {\r\n"
				+ "    return this;\r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "";
		
		return content;
		
	}

	public String generateJavaUseCase(IUseCase usecase) {
		
		String content = "package vistas;\r\n"
				+ "\r\n"
				+ "import com.vaadin.flow.component.Tag;\r\n"
				+ "import com.vaadin.flow.component.dependency.JsModule;\r\n"
				+ "import com.vaadin.flow.component.littemplate.LitTemplate;\r\n"
				+ "\r\n"
				+ "@Tag(\"vista-"+clean(usecase.getName()).toLowerCase()+"\")\r\n"
				+ "@JsModule(\"./views/vista-"+clean(usecase.getName()).toLowerCase()+".ts\")\r\n"
				+ "public class Vista"+upper(clean(usecase.getName()).toLowerCase())+" extends LitTemplate {\r\n"
				+ "\r\n"
				+ "    public Vista"+upper(clean(usecase.getName()).toLowerCase())+"() {\r\n"
				+ "        // You can initialise any data required for the connected UI components here.\r\n"
				+ "    }\r\n"
				+ "\r\n"
				+ "}";
		
		return content;
		
	}
	
public String generateJavaUseCaseItem(IUseCase usecase) {
		
		String content = "package vistas;\r\n"
				+ "\r\n"
				+ "import com.vaadin.flow.component.Tag;\r\n"
				+ "import com.vaadin.flow.component.dependency.JsModule;\r\n"
				+ "import com.vaadin.flow.component.littemplate.LitTemplate;\r\n"
				+ "\r\n"
				+ "@Tag(\"vista-"+clean(usecase.getName()).toLowerCase()+"_item\")\r\n"
				+ "@JsModule(\"./views/vista-"+clean(usecase.getName()).toLowerCase()+"_item.ts\")\r\n"
				+ "public class Vista"+upper(clean(usecase.getName()).toLowerCase())+"_item extends LitTemplate {\r\n"
				+ "\r\n"
				+ "    public Vista"+upper(clean(usecase.getName()).toLowerCase())+"_item() {\r\n"
				+ "        // You can initialise any data required for the connected UI components here.\r\n"
				+ "    }\r\n"
				+ "\r\n"
				+ "}";
		
		return content;
		
	}

public String generateTSActor(IActor actor) {
	
	String content = "import { LitElement, html, css, customElement } from 'lit-element';\r\n"
			+ "\r\n"
			+ "@customElement('vista-"+clean(actor.getName()).toLowerCase()+"')\r\n"
			+ "export class "+"Vista"+upper(clean(actor.getName()).toLowerCase())+" extends LitElement {\r\n"
			+ "  static get styles() {\r\n"
			+ "    return css`\r\n"
			+ "      :host {\r\n"
			+ "          display: block;\r\n"
			+ "          height: 100%;\r\n"
			+ "      }\r\n"
			+ "      `;\r\n"
			+ "  }\r\n"
			+ "\r\n"
			+ "  render() {\r\n"
			+ "    return html``;\r\n"
			+ "  }\r\n"
			+ "\r\n"
			+ "  // Remove this method to render the contents of this view inside Shadow DOM\r\n"
			+ "  createRenderRoot() {\r\n"
			+ "    return this;\r\n"
			+ "  }\r\n"
			+ "}\r\n"
			+ "";
	
	return content;
	
}

public String generateJavaActor(IActor actor) {
	
	String content = "package vistas;\r\n"
			+ "\r\n"
			+ "import com.vaadin.flow.component.Tag;\r\n"
			+ "import com.vaadin.flow.component.dependency.JsModule;\r\n"
			+ "import com.vaadin.flow.component.littemplate.LitTemplate;\r\n"
			+ "\r\n"
			+ "@Tag(\"vista-"+clean(actor.getName()).toLowerCase()+"\")\r\n"
			+ "@JsModule(\"./views/vista-"+clean(actor.getName()).toLowerCase()+".ts\")\r\n"
			+ "public class Vista"+upper(clean(actor.getName()).toLowerCase())+" extends LitTemplate {\r\n"
			+ "\r\n"
			+ "    public Vista"+upper(clean(actor.getName()).toLowerCase())+"() {\r\n"
			+ "        // You can initialise any data required for the connected UI components here.\r\n"
			+ "    }\r\n"
			+ "\r\n"
			+ "}";
	
	return content;
	
}

 public Boolean inheritanceUseCases(IUseCase usecase) {
	 int count = 0;
	 Iterator genIter = usecase.toRelationshipIterator();
	 while (genIter.hasNext()) {
			IRelationship relationship = (IRelationship) genIter.next();
			if (relationship.getModelType() == "Generalization") {
				count = count+1;
			}
	 }
	 return count==0;
 }
 
 public Boolean inheritanceActors(IActor actor) {
	 int count = 0;
	 Iterator genIter = actor.toRelationshipIterator();
	 while (genIter.hasNext()) {
			IRelationship relationship = (IRelationship) genIter.next();
			if (relationship.getModelType() == "Generalization") {
				count = count+1;
			}
	 }
	 return count==0;
 }

	public void update(VPAction action) {
	}
}
