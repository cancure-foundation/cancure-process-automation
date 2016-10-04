package org.cancure.cpa.service;

import java.security.SecureRandom;
import java.util.Random;

import org.cancure.cpa.persistence.entity.InvestigatorType;
import org.cancure.cpa.persistence.entity.ListOfValues;
import org.cancure.cpa.persistence.entity.Settings;
import org.cancure.cpa.persistence.repository.InvestigatorTypeRepository;
import org.cancure.cpa.persistence.repository.ListOfValuesRepository;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonServiceImpl implements CommonService {

	@Autowired
	private InvestigatorTypeRepository investigatorTypeRepository;
	
	@Autowired
	private ListOfValuesRepository listOfValuesRepository;
	
	@Autowired
	private SettingsRepository settingsRepository;
	
    private static final Random RANDOM = new SecureRandom();
	
	@Override
	public Iterable<InvestigatorType> getInvestigatorTypes() {
		return investigatorTypeRepository.findAll();
	}

	@Override
	public Iterable<ListOfValues> getListOfValues(String listName) {
		return listOfValuesRepository.findByName(listName);
	}

	@Override
	public Iterable<Settings> findAllSettings() {
		return settingsRepository.findAllByOrderByIdAsc();
	}

	@Override
	public Settings saveSetting(Settings setting) {
		return settingsRepository.save(setting);
	}

	@Override
	public Settings findSettingsById(Integer id) {
		return settingsRepository.findOne(id);
	}

    @Override
    public String generatePassword() {

        String letters = "ABCDEFGHJKMNPQRSTUVWXYZ2345678910";
        String pw = "";
        for (int i=0; i<8; i++)
        {
            int index = (int)(RANDOM.nextDouble()*letters.length());
            pw += letters.substring(index, index+1);
        }
        return pw;
    }
    }


