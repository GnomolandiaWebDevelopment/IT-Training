<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>

<html>

		<div class="row card panel" style="padding: 30px;">
	      <div class="row">
	      	<h6 class="teal-text">Domanda presso <c:out value="${domanda.azienda.getNome()}"/></h6>
	      	<div class=" row">
	      		 <p>Numero di CFU: <c:out value="${domanda.cfu}"/></p>
	      	</div>
	      	
	      	<div class=" row">
	      		 <p>Data inizio: <c:out value="${domanda.inizioTirocinio.getDayOfMonth()}"/>/<c:out value="${domanda.inizioTirocinio.getMonthValue()}"/>/<c:out value="${domanda.inizioTirocinio.getYear()}"/></p>
	      	</div>
	      	
	      	<div class=" row">
	      		 <p>Data fine: <c:out value="${domanda.fineTirocinio.getDayOfMonth()}"/>/<c:out value="${domanda.fineTirocinio.getMonthValue()}"/>/<c:out value="${domanda.fineTirocinio.getYear()}"/></p>
	      	</div>
	      	
	      	<div class=" row">
	      		 <p>Numero ore svolte: <c:out value="${domanda.getNumeroOre() / 60}"/></p>
	      	</div>
	    </div>
	      
	      <table>
        <thead>
          <tr>
              <th>Data</th>
              <th>Ora inizio</th>
              <th>Ora fine</th>
              <th>Descrizione</th>
              <th>Elimina attivit�</th>
          </tr>
        </thead>
		
		
        <tbody>
        	<c:forEach items="${domanda.getRegistri()}" var="registro" varStatus="loop">
	          <tr>
	            <td><c:out value="${registro.data.getDayOfMonth()}"/>/<c:out value="${registro.data.getMonthValue()}"/>/<c:out value="${registro.data.getYear()}"/></td>
	            <td><c:out value="${registro.inizio}"/></td>
	            <td><c:out value="${registro.fine}"/></td>
	            <td><c:out value="${registro.descrizione}"/></td>
	            <td><a href="#modal${registro.id}" class="modal-trigger waves-effect waves-light btn-flat"><i class="material-icons right">delete</i></a></td>
	          </tr>
	        </c:forEach>
	        
		</tbody>
      </table>
		
			    <c:forEach items="${domanda.getRegistri()}" var="registro" varStatus="loop">
	        		  <!-- Modal Structure -->
					  <div id="modal${registro.id}" class="modal">
					    <div class="modal-content">
					      <h5 class="teal-text">Elimina attivit�</h5>
					      <p>Sei sicuro di voler eliminare questa attivit� dal tuo registro?</p>
					    </div>
					    <div class="modal-footer">						      
					    	  <a href="/home/registro-form/cancella-tirocinio?id=${registro.id}&idDomanda=${domanda.id}" class="waves-effect waves-light btn-flat">S�</a>
						      <a href="#" class="modal-close waves-effect waves-light btn-flat">No</a>
					    </div>
					  </div>
				</c:forEach>

					  
	      <div class="row"></div>
	      <div class="row"></div>
          <div class="row">
		    <form:form class="col s12" id="registroForm" action="/home/registro-form/compila-registro" method="POST" modelAttribute="registroForm">
		    		<form:input path="idDomanda" type="hidden" value="${domanda.id}"/>
				      <div class="row">
				        <div class="input-field col s3">
				          <form:input path="giornoInizio" id="giornoInizio" type="hidden" class="validate" value="${domanda.inizioTirocinio.getDayOfMonth()}" />
				        </div>
				        <div class="input-field col s3">
				          <form:input path="meseInizio" id="meseInizio" type="hidden" class="validate" value="${domanda.inizioTirocinio.getMonthValue()}" />
				        </div>
				        <div class="input-field col s3">
				          <form:input path="annoInizio" id="annoInizio" type="hidden" class="validate" value="${domanda.inizioTirocinio.getYear()}" />
				        </div>
				      </div>
				      <div class="row">
				        <div class="input-field col s3">
				          <form:input path="giornoFine" id="giornoFine" type="hidden" class="validate" value="${domanda.fineTirocinio.getDayOfMonth()}"/>
				        </div>
				        <div class="input-field col s3">
				          <form:input path="meseFine" id="meseFine" type="hidden" class="validate" value="${domanda.fineTirocinio.getMonthValue()}"/>
				        </div>
				        <div class="input-field col s3">
				          <form:input path="annoFine" id="annoFine" type="hidden" class="validate" value="${domanda.fineTirocinio.getYear()}"/>
				        </div>
				      </div>		
				      
				      	      
		        <div class="row date-input">
		          <div class="col s12">
		          </div>
		          <div class="input-field col s4">
		            <i class="material-icons prefix">event_note</i>
		            <form:input path="giorno" id="giorno"  type="text" class="validate"/>
		              <label for="giorno">Giorno</label>
		          </div>
		          <div class="input-field col s5">
		            <form:select path="mese" class="icons" id="mese">
		              <form:option value="1" data-icon="resources/images/calendar.svg">
		                Gennaio
		              </form:option>
		              <form:option value="2" data-icon="resources/images/calendar.svg">
		              	Febbraio
		              </form:option>
		              <form:option value="3" data-icon="resources/images/calendar.svg">
		                Marzo
		              </form:option>
		              <form:option value="4" data-icon="resources/images/calendar.svg">
		                Aprile
		              </form:option>
		              <form:option value="5" data-icon="resources/images/calendar.svg">
		                Maggio
		              </form:option>
		              <form:option value="6" data-icon="resources/images/calendar.svg">
		                Giugno
		              </form:option>
		              <form:option value="7" data-icon="resources/images/calendar.svg">
		                Luglio
		              </form:option>
		              <form:option value="8" data-icon="resources/images/calendar.svg">
		                Agosto
		              </form:option>
		              <form:option value="9" data-icon="resources/images/calendar.svg">
		                Settembre
		              </form:option>
		              <form:option value="10" data-icon="resources/images/calendar.svg">
		               Ottobre
		              </form:option>
		              <form:option value="11" data-icon="resources/images/calendar.svg">
		               Novembre
		              </form:option>
		              <form:option value="12" data-icon="resources/images/calendar.svg">
		               Dicembre
		              </form:option>
		            </form:select>
		          </div>
		          <div class="input-field col s3">
		            <form:input path="anno"
		                        id="anno"  type="text" class="validate"/>
		                        <label for="anno">Anno</label>  
		          </div>
		          
		          <div class="input-field col s12">
			          <form:errors path="anno" cssClass="helper-text red-text chip" />  
		          </div>
		        </div>
		        
		        <jsp:include page="/WEB-INF/views/forms/ora-inizio-registro.jsp" />
		        <jsp:include page="/WEB-INF/views/forms/ora-fine-registro.jsp" />
		        
		        					<div class="input-field col s12">
							          <form:textarea path="descrizione" id="textarea1" class="materialize-textarea"></form:textarea>
							          <label for="textarea1">Inserisci descrizione attivit� svolte</label>
					    			</div>
				
	      		<div class="row"></div>
	      		<div class="row"></div>
	      		
				          <div class="row right">
							  <button class="btn waves-effect waves-light" type="submit" name="registro_submit">Compila
							    <i class="material-icons right">send</i>
							  </button>
						  </div>
						  
		    </form:form>
		  </div>
		  </div>
	      <div class="row"></div>
	      <div class="row"></div>
      
</html>