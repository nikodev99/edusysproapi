package com.edusyspro.api.controller;

import com.edusyspro.api.model.Settings;
import com.edusyspro.api.service.interfaces.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PostMapping()
    ResponseEntity<Settings> saveOrUpdateSetting(@RequestBody Settings settings) {
        return ResponseEntity.ok(settingsService.save(settings));
    }

    @GetMapping("/{schoolId}")
    ResponseEntity<List<Settings>> getSettings(@PathVariable String schoolId) {
        return ResponseEntity.ok(settingsService.fetchAll(schoolId));
    }

    @GetMapping("/{schoolId}/{key}")
    ResponseEntity<Settings> getSetting(@PathVariable String schoolId, @PathVariable String key) {
        return ResponseEntity.ok(settingsService.fetchOneByCustomColumn(key, schoolId));
    }
}
