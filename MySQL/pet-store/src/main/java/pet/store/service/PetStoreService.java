package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId(); 
		PetStore petStore;
		//= findOrCreatePetStore(petStoreData.getPetStoreId());
		
		if(petStoreId == null) {
			petStore = findOrCreatePetStore(petStoreId);
		}
		else {
			petStore = findPetStoreById(petStoreId); 
			if(petStore == null) {
				throw new NoSuchElementException("Pet store is not found with ID = " + petStoreId); 
			}
		}
		
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore)); 
		
	}

	// setFields in Contributor method guide I
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		// Example: contributor.setContributorEmail(contributorData.getContributorEmail());	
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone()); 
		
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore;
		
		if(Objects.isNull(petStoreId)) {
			
			petStore = new PetStore();
			
		} else {
			petStore = findPetStoreById(petStoreId); 
		}
		return petStore; 
	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId).orElse(null); 
	}
}
