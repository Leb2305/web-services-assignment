package musiclessonservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import musiclessonservice.model.Student;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component 
public class PupilTrackerImpl implements PupilTracker{

    private RestClient client;
    private ObjectMapper map;

    public PupilTrackerImpl(){
        this.client = RestClient.create("https://pmaier.eu.pythonanywhere.com/plt");
        this.map = new ObjectMapper();
    }

    @Override
    public Student getPupil(int matric) {
        try{
            JsonNode response = client.get().uri("/pupil/{matric}", matric).retrieve().body(JsonNode.class);
            JsonNode pupilNode = response.get("pupil");
            if(response == null || pupilNode == null || pupilNode.isNull()){
                return null;
            }
            else{
                return map.treeToValue(pupilNode, Student.class);
            }

        }
        catch(HttpClientErrorException.NotFound e){
            return null;
        }
        catch(RestClientException e){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Pupil Tracker is unavailable");
        }
    }


}
