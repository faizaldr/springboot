package com.inixindo.market.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inixindo.market.dto.ListingRequest;
import com.inixindo.market.dto.ListingResponse;
import com.inixindo.market.model.Listing;
import com.inixindo.market.repository.ListingRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListingService {

    // repo
    private final ListingRepository repo;

    // fungsi tambah data
    @Transactional
    public String tambah(ListingRequest request){
        Listing baru = new Listing();
        baru.setJudul(request.judul());
        baru.setDeskripsi(request.deskripsi());
        baru.setHarga(request.harga());
        baru.setKategori(request.kategori());
        baru.setUsername(request.username());
        baru.setNohp(request.nohp());

        // simpan
        repo.save(baru);

        return "berhasil input data";
    }

    // ambil data listing
    // @Transactional
    // public List<Listing> tampilListing(){
    //     return repo.findAll();
    // }

    @Transactional
    public ListingResponse tampilListing(){
        Optional<Listing> listing = repo.findAll().stream().findFirst();
        return listing.map( val -> new ListingResponse(val.getJudul(), val.getDeskripsi(), val.getHarga())).orElse(null);
    }
    
}
