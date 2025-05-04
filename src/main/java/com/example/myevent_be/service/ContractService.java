package com.example.myevent_be.service;

import com.example.myevent_be.dto.request.ContractRequest;
import com.example.myevent_be.dto.request.ContractUpdateRequest;
import com.example.myevent_be.dto.response.ContractResponse;
import com.example.myevent_be.entity.Contract;
import com.example.myevent_be.entity.Customer;
import com.example.myevent_be.entity.Rental;
import com.example.myevent_be.enums.ContractStatus;
import com.example.myevent_be.exception.AppException;
import com.example.myevent_be.exception.ErrorCode;
import com.example.myevent_be.mapper.ContractMapper;
import com.example.myevent_be.repository.ContractRepository;
import com.example.myevent_be.repository.CustomerRepository;
import com.example.myevent_be.repository.LocationRepository;
import com.example.myevent_be.repository.RentalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ContractService {
//    private final ContractRepository contractRepository;
//    private final CustomerRepository customerRepository;
//    private final LocationRepository locationRepository;
//    private final RentalRepository rentalRepository;
//    private final ContractMapper contractMapper;
//
//    @Transactional
//    public ContractResponse createContract(ContractRequest request) {
//        log.info("Creating contract with payment intent id: {}", request.getPaymentIntentId());
//
//        // Kiểm tra contract đã tồn tại chưa
//        contractRepository.findByPaymentIntentId(request.getPaymentIntentId())
//                .ifPresent(contract -> {
//                    log.warn("Contract already exists with payment intent id: {}", request.getPaymentIntentId());
//                    throw new AppException(ErrorCode.CONTRACT_ALREADY_EXISTED);
//                });
//
//        // Lấy tên địa danh từ locationId
//        String provinceName = locationRepository.findNameById(request.getProvinceId());
//        String districtName = locationRepository.findNameById(request.getDistrictId());
//        String wardName = locationRepository.findNameById(request.getWardId());
//        String fullAddress = request.getStreet() + ", " + wardName + ", " + districtName + ", " + provinceName;
//
//        // Tạo hoặc cập nhật customer
//        Customer customer = customerRepository.findByPhoneNumber(request.getCustomerPhone())
//                .orElseGet(Customer::new);
//        customer.setName(request.getCustomerName());
//        customer.setPhone_number(request.getCustomerPhone());
//        customer.setAddress(fullAddress);
//        customerRepository.save(customer);
//
//        // Lấy rental từ rentalId nếu có
//        Rental rental = null;
//        if (request.getRentalId() != null) {
//            rental = rentalRepository.findById(request.getRentalId())
//                .orElseThrow(() -> new AppException(ErrorCode.RENTAL_NOT_FOUND));
//        }
//
//        Contract contract = new Contract();
//        contract.setName(request.getName());
//        contract.setCustomer(customer);
//        contract.setPaymentIntentId(request.getPaymentIntentId());
//        if (rental != null) {
//            contract.setRental(rental);
//        }
//        Contract savedContract = contractRepository.saveAndFlush(contract);
//        log.info("Created contract with id: {}", savedContract.getId());
//
//        // Trả về response bằng mapper để đảm bảo mapping đúng các trường ngày tháng
//        ContractResponse response = contractMapper.toContractResponse(savedContract);
//        response.setEventTime(request.getEventTime());
////        response.setStatus(request.getStatus().valueOf(""));
//        if (request.getStatus() != null) {
////            contract.setStatus(ContractStatus.valueOf(request.getStatus()));
//            contractMapper.updateContract(contract, request);
//        }
//        response.setRentalId(request.getRentalId());
//        return response;
//    }
//
////    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
//    public List<ContractResponse> getContracts() {
//        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
//        if (role.equals("ADMIN") || role.equals("MANAGER")) {
//            return contractRepository.findAll().stream()
//                    .map(contractMapper::toContractResponse)
//                    .toList();
//        } else {
//            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//            return contractRepository.findByCustomerId(userId).stream()
//                    .map(contractMapper::toContractResponse)
//                    .toList();
//        }
//    }
//
//    public ContractResponse getContractById(String contractId) {
//        Contract contract = contractRepository.findById(contractId)
//                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_FOUND));
//
//        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
//        if (!role.equals("ADMIN") || role.equals("MANAGER")) {
//            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//            if (!contract.getCustomer().getId().equals(userId)) {
//                throw new AppException(ErrorCode.CONTRACT_NOT_FOUND);
//            }
//        }
//
//        return contractMapper.toContractResponse(contract);
//    }
//
//    @Transactional
//    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
//    public ContractResponse updateContract(String contractId, ContractUpdateRequest request) {
//        log.info("Updating status for contract id: {} to status: {}", contractId, request.getStatus());
//        Contract contract = contractRepository.findById(contractId)
//                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_FOUND));
//
//        contract.setStatus(request.getStatus());
//        Contract updatedContract = contractRepository.save(contract);
//        log.info("Updated status for contract id: {}", contractId);
//
//        return contractMapper.toContractResponse(updatedContract);
//    }
////    public ContractResponse updateContract(String contractId, ContractUpdateRequest contractUpdateRequest) {
////        Contract existingContract = contractRepository.findById(contractId)
////                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_FOUND));
////        contractMapper.updateContract(existingContract, contractUpdateRequest);
////        Contract updatedContract = contractRepository.save(existingContract);
////        return contractMapper.toContractResponse(updatedContract);
////    }
//
//    @Transactional
//    public void deleteContract(String contractId) {
//        if (!contractRepository.existsById(contractId)) {
//            throw new AppException(ErrorCode.CONTRACT_NOT_FOUND);
//        }
//        contractRepository.deleteById(contractId);
//    }
//
//    public ContractResponse getContractByPaymentIntentId(UUID paymentIntentId) {
//        return contractRepository.findByPaymentIntentId(paymentIntentId)
//                .map(contractMapper::toContractResponse)
//                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_FOUND));
//    }
//}

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;
    private final RentalRepository rentalRepository;
    private final ContractMapper contractMapper;

    @Transactional
    public ContractResponse createContract(ContractRequest request) {
        log.info("Creating contract with payment intent id: {}", request.getPaymentIntentId());

        // Kiểm tra contract đã tồn tại chưa
        contractRepository.findByPaymentIntentId(request.getPaymentIntentId())
                .ifPresent(contract -> {
                    log.warn("Contract already exists with payment intent id: {}", request.getPaymentIntentId());
                    throw new AppException(ErrorCode.CONTRACT_ALREADY_EXISTED);
                });

        // Lấy tên địa danh từ locationId
        String provinceName = locationRepository.findNameById(request.getProvinceId());
        String districtName = locationRepository.findNameById(request.getDistrictId());
        String wardName = locationRepository.findNameById(request.getWardId());
        String fullAddress = request.getStreet() + ", " + wardName + ", " + districtName + ", " + provinceName;

        // Tạo hoặc cập nhật customer
        Customer customer = customerRepository.findByPhoneNumber(request.getCustomerPhone())
                .orElseGet(Customer::new);
        customer.setName(request.getCustomerName());
        customer.setPhone_number(request.getCustomerPhone());
        customer.setAddress(fullAddress);
        customerRepository.save(customer);

        // Lấy rental từ rentalId nếu có
        Rental rental = null;
        if (request.getRentalId() != null) {
            rental = rentalRepository.findById(request.getRentalId())
                    .orElseThrow(() -> new AppException(ErrorCode.RENTAL_NOT_FOUND));
        }

        Contract contract = new Contract();
        contract.setName(request.getName());
        contract.setCustomer(customer);
        contract.setPaymentIntentId(request.getPaymentIntentId());
        if (request.getStatus() != null) {
            contract.setStatus(ContractStatus.valueOf(request.getStatus()));
        }
        if (rental != null) {
            contract.setRental(rental);
        }
        Contract savedContract = contractRepository.saveAndFlush(contract);
        log.info("Created contract with id: {}", savedContract.getId());

        // Trả về response bằng mapper để đảm bảo mapping đúng các trường ngày tháng
        ContractResponse response = contractMapper.toContractResponse(savedContract);
        response.setEventTime(request.getEventTime());
        response.setRentalId(request.getRentalId());
        return response;
    }

    //    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public List<ContractResponse> getContracts() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        if (role.equals("ADMIN") || role.equals("MANAGER")) {
            return contractRepository.findAll().stream()
                    .map(contractMapper::toContractResponse)
                    .toList();
        } else {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            return contractRepository.findByCustomerId(userId).stream()
                    .map(contractMapper::toContractResponse)
                    .toList();
        }
    }

    public ContractResponse getContractById(String contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_FOUND));

        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        if ((role.equals(""))) {

            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!contract.getCustomer().getId().equals(userId)) {
                throw new AppException(ErrorCode.CONTRACT_NOT_FOUND);
            }
            log.info("id contract: " + contractId);
        }

        return contractMapper.toContractResponse(contract);
    }

    @Transactional
     @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
     public ContractResponse updateContract(String contractId, ContractUpdateRequest request) {
         log.info("Updating status for contract id: {} to status: {}", contractId, request.getStatus());
         Contract contract = contractRepository.findById(contractId)
                 .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_FOUND));
 
         log.info("Current contract status: {}", contract.getStatus());
         contractMapper.updateContract(contract, request);
         log.info("Updated contract status to: {}", contract.getStatus());
 
         Contract updatedContract = contractRepository.saveAndFlush(contract);
         log.info("Saved contract with status: {}", updatedContract.getStatus());
 
         // Refresh lại entity từ database để đảm bảo dữ liệu mới nhất
         Contract refreshedContract = contractRepository.findById(updatedContract.getId())
         log.info("Refreshed contract status from database: {}", refreshedContract.getStatus());
 
         return contractMapper.toContractResponse(refreshedContract);
     }
//    public ContractResponse updateContract(String contractId, ContractUpdateRequest contractUpdateRequest) {
//        Contract existingContract = contractRepository.findById(contractId)
//                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_FOUND));
//        contractMapper.updateContract(existingContract, contractUpdateRequest);
//        Contract updatedContract = contractRepository.save(existingContract);
//        return contractMapper.toContractResponse(updatedContract);
//    }

    @Transactional
    public void deleteContract(String contractId) {
        if (!contractRepository.existsById(contractId)) {
            throw new AppException(ErrorCode.CONTRACT_NOT_FOUND);
        }
        contractRepository.deleteById(contractId);
    }

    public ContractResponse getContractByPaymentIntentId(UUID paymentIntentId) {
        return contractRepository.findByPaymentIntentId(paymentIntentId)
                .map(contractMapper::toContractResponse)
                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_FOUND));
    }
}