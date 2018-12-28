<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
 <!-- Registrazione Tutor Accademico -->
			    <div class="collapsible-header">
			      <i class="material-icons">supervisor_account</i>
			      Registrati come tutor accademico
			      </div>
			    <div class="collapsible-body">
			  <div class="row">
			    <form:form cssClass="col s12" action ="/richiesta-registrazione-accademico" method = "POST" modelAttribute = "registrazioneAccademico">
			      <div class="row">
			        <div class="input-field col s6">
			          <i class="material-icons prefix"></i>
			          <form:input path="nome" id="first_name_accademico" type="text" cssClass="validate"/>
			          <label for="first_name_accademico">Nome</label>
			          <form:errors path="nome" cssClass="helper-text red-text chip" />
			        </div>
			        
			        <div class="input-field col s6">
			          <i class="material-icons prefix"></i>
			          <form:input path="cognome" id="last_name_accademico" type="text" cssClass="validate"/>
			          <label for="last_name_accademico">Cognome</label>
			          <form:errors path="cognome" cssClass="helper-text red-text chip" />
			        </div>
			      </div>
			      
				  <div class="row">
					  <div class="input-field col s6">
					  	<i class="material-icons prefix">wc</i>
					    <form:select path="sesso" id="sesso_accademico">
					      <option value="M">M</option>
					      <option value="F">F</option>
					      <form:errors path="sesso" cssClass="helper-text red-text chip" />
					    </form:select>
					  </div>
				  </div>
				  
			      
        <div class="row date-input">
          <div class="col s12">
          </div>
          <div class="input-field col s4">
            <i class="material-icons prefix">cake</i>
            <form:input
              path="giornoNascita" id="iscrizione-giornoDiNascita-accademico" />  
          </div>
          <div class="input-field col s5">
            <form:select path="meseNascita" id="iscrizione-meseDiNascita-accademico">
              <form:option value="" disabled="true" selected="selected">
              </form:option>
              <form:option value="1">
                Gennaio
              </form:option>
              <form:option value="2">
              	Febbraio
              </form:option>
              <form:option value="3">
                Marzo
              </form:option>
              <form:option value="4">
                Aprile
              </form:option>
              <form:option value="5">
                Maggio
              </form:option>
              <form:option value="6">
                Giugno
              </form:option>
              <form:option value="7">
                Luglio
              </form:option>
              <form:option value="8">
                Agosto
              </form:option>
              <form:option value="9">
                Settembre
              </form:option>
              <form:option value="10">
               Ottobre
              </form:option>
              <form:option value="11">
               Novembre
              </form:option>
              <form:option value="12">
               Dicembre
              </form:option>
            </form:select>
          </div>
          <div class="input-field col s3">
            <form:input
                        path="annoNascita"
                        id="iscrizione-annoDiNascita-accademico" /> 
          </div>
          
          <div class="input-field col s6">
            <form:errors path="giornoNascita" cssClass="helper-text red-text chip" />
          </div>
        </div>
			      
				  <div class="row">
				     <div class="input-field col s6">
				       <i class="material-icons prefix">account_circle</i>
				       <form:input path="username" id="username-accademico" type="text" cssClass="validate"/>
				       <label for="username-accademico">Username</label>
				       <form:errors path="username" cssClass="helper-text red-text chip" />
				     </div>
				  </div>
				  
			      <div class="row">
			        <div class="input-field col s6">
			          <i class="material-icons prefix">mail</i>
			          <form:input path="email" id="email_accademico" type="email" cssClass="validate"/>
			          <label for="email_accademico">Email</label>
			          <form:errors path="email" cssClass="helper-text red-text chip" />
			        </div>
			      </div>
			      
			      <div class="row">
			        <div class="input-field col s6">
			          <i class="material-icons prefix">lock</i>
			          <form:input path="password" id="password_accademico" type="password" cssClass="validate"/>
			          <label for="password_accademico">Password</label>
			          
			        </div>
			      </div>
			      
			      <div class="row">
			        <div class="input-field col s6">
			          <i class="material-icons prefix">lock_outline</i>
			          <form:input path="confermaPassword" id="confirm_password_accademico" type="password" cssClass="validate"/>
			          <label for="confirm_password_accademico">Conferma password</label>
			          <form:errors path="password" cssClass="helper-text red-text chip" />
			        </div>
			      </div>
			      
				  <button class="btn waves-effect waves-light" type="submit" name="action_studente">Registrati
				    <i class="material-icons right">send</i>
				  </button>
			    </form:form>
		  	</div>
			    
		  </div>
</html>