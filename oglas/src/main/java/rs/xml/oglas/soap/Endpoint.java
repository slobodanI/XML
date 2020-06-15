package rs.xml.oglas.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import rs.xml.oglas.xsd.Faculty;
import rs.xml.oglas.xsd.GetStudentRequest;
import rs.xml.oglas.xsd.GetStudentResponse;
import rs.xml.oglas.xsd.Student;



@org.springframework.ws.server.endpoint.annotation.Endpoint
public class Endpoint {
	
	private static final String NAMESPACE_URI = "http://xml.rs/oglas/xsd";
	
	@Autowired
	public Endpoint () {
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentRequest")
	@ResponsePayload
	public GetStudentResponse getStudent(@RequestPayload GetStudentRequest request) {
		System.out.println("*** getStudent:" + request.getName());
		GetStudentResponse response = new GetStudentResponse();
		
		Student student = new Student();
		student.setFaculty(Faculty.FTN);
		student.setName("PERA");
		student.setSurname("PERIC");
		student.setYear(4);
		
		response.setStudent(student);

		return response;
	}
	
}
